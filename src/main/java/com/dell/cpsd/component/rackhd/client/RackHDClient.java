/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.client;

import com.dell.cpsd.component.common.cli.CliBase;
import com.dell.cpsd.component.common.validator.ValidationResult;
import com.dell.cpsd.component.rackhd.RackHdException;
import com.dell.cpsd.component.rackhd.config.ClientConfig;
import com.dell.cpsd.component.rackhd.config.ServiceConfig;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.domain.model.RackHDModel;
import com.dell.cpsd.component.rackhd.request.UpdateFirmwareWorkflowRequest;
import com.dell.cpsd.component.rackhd.service.RackHdDiscoveryService;
import com.dell.cpsd.component.rackhd.service.RackHdInstallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This is the RackHD Client class It contains Client CLI Rack HD.
 *
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 * </p>
 * 
 * @version 1.0
 * 
 * @since 1.0
 */
@Component
public class RackHDClient extends CliBase<RackHDClientArgs, RackHDModel>
{
    private static final String PROGRAM_NAME = "DELL EMC RackHD Client";

    @Autowired
    @Qualifier("rackHdDiscoveryService")
    RackHdDiscoveryService rackHdDiscoveryService;

    @Autowired
    @Qualifier("rackHdInstallerService")
    RackHdInstallerService rackHdInstallerService;

    public RackHDClient()
    {
        super(PROGRAM_NAME, new RackHDClientArgs());
    }

    public RackHDClient(String programName, RackHDClientArgs cliParameters)
    {
        super(programName, cliParameters);
    }

    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(ClientConfig.class, ServiceConfig.class);

        context.refresh();

        RackHDClient client = (RackHDClient) context.getBean("rackHdClient");

        client.run(args);
    }

    @Override
    protected String getPreRunOutputMessage()
    {
        return "Discovers all Nodes inside a RackHD System";
    }

    @Override
    protected void validateParameters()
    {

    }

    @Override
    protected RackHDModel invokeService() throws Exception
    {
        RackHdCriteria criteria = createCriteria(cliParameters);
        ValidationResult validationResult = rackHdDiscoveryService.validate(criteria);

        final UpdateFirmwareWorkflowRequest workflowRequest = new UpdateFirmwareWorkflowRequest(
                criteria.getUsername(), criteria.getPassword(),"/home/vce/firmware/BIOS_605TD_WN64_2.1.5.EXE");

        // nodeID should be passed in via the prompt
        rackHdInstallerService.postWorkflowForNode(workflowRequest, criteria, "5975fe06449fb0eb7a70d07a");

        if (validationResult.isSuccess())
        {
            return rackHdDiscoveryService.discover(criteria);
        }
        else
        {
            throw new RackHdException("Validation failed: " + validationResult.toString());
        }
    }

    private RackHdCriteria createCriteria(RackHDClientArgs args)
    {
        RackHdCriteria criteria = new RackHdCriteria(args.getIpAddress(), args.getUserName(), args.getPassword(), args.getPort(),
                args.isHttp(), args.isAuth());
        return criteria;
    }
}
