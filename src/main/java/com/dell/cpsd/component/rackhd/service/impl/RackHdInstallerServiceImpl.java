/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.service.impl;

import com.dell.cpsd.component.common.rest.CustomRestTemplate;
import com.dell.cpsd.component.rackhd.RackHdException;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.enums.Endpoints;
import com.dell.cpsd.component.rackhd.request.UpdateFirmwareWorkflowRequest;
import com.dell.cpsd.component.rackhd.response.GetWorkflowResponse;
import com.dell.cpsd.component.rackhd.response.UpdateFirmwareWorkflowResponse;
import com.dell.cpsd.component.rackhd.service.RackHdAuthenticationService;
import com.dell.cpsd.component.rackhd.service.RackHdInstallerService;
import com.dell.cpsd.component.rackhd.utils.ApplicationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * This is the Rack HD Installer Service Implementation. It is used for installing updates to various components discovered by Rack HD.
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
public class RackHdInstallerServiceImpl implements RackHdInstallerService
{
    private static final Logger LOGGER = Logger.getLogger(RackHdInstallerServiceImpl.class);

    private CustomRestTemplate          restTemplate;

    private RackHdAuthenticationService rackHdAuthenticationService;

    public RackHdInstallerServiceImpl(CustomRestTemplate restTemplate, RackHdAuthenticationService rackHdAuthenticationService)
    {
        this.restTemplate = restTemplate;
        this.rackHdAuthenticationService = rackHdAuthenticationService;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     */
    @Override
    public UpdateFirmwareWorkflowResponse postWorkflowForNode(final UpdateFirmwareWorkflowRequest workflowRequest,
            final RackHdCriteria rackHdCriteria, final String nodeId) throws RackHdException
    {
        UpdateFirmwareWorkflowResponse response = null;

        try
        {
            HttpHeaders httpHeaders = rackHdAuthenticationService.authenticateAndCreateHeaders(rackHdCriteria);
            String targetUrl = ApplicationUtil.formatEndpointUrl(rackHdCriteria, Endpoints.UPDATE_FIRMWARE_V2, nodeId);

            ObjectMapper mapper = new ObjectMapper();
            String requestInString = "{\"options\":{\"defaults\":" + mapper.writeValueAsString(workflowRequest) + "}}";

            HttpEntity<String> request = new HttpEntity<>(requestInString, httpHeaders);
            response = restTemplate.postForEntity(targetUrl, request, UpdateFirmwareWorkflowResponse.class).getBody();
        }
        catch (HttpStatusCodeException hSCE) {
            LOGGER.error("Status code is: " + hSCE.getStatusCode() +
                    "Response body is: " + hSCE.getResponseBodyAsString());

            throw new RackHdException(hSCE);
        }
        catch (RestClientException e)
        {
            LOGGER.error("Cause is: " + e.getCause() +
                    "Message is: " + e.getMessage());
            throw new RackHdException(e);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     */
    @Override
    public List<GetWorkflowResponse> getWorkflowsForNode(final String nodeIdentifier) throws RackHdException
    {
        return null;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     */
    @Override
    public GetWorkflowResponse getWorkflow(final String instanceId, final RackHdCriteria rackHdCriteria) throws RackHdException
    {
        GetWorkflowResponse response = null;
        try
        {
            HttpHeaders httpHeaders = rackHdAuthenticationService.authenticateAndCreateHeaders(rackHdCriteria);
            String targetUrl = ApplicationUtil.formatEndpointUrl(rackHdCriteria, Endpoints.WORKFLOWS_V2, instanceId);
            response = restTemplate.getForEntity(targetUrl, httpHeaders, GetWorkflowResponse.class).getBody();
        }
        catch (RestClientException e)
        {
            throw new RackHdException(e);
        }
        return response;
    }
}
