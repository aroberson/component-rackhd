/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.service;

import com.dell.cpsd.component.rackhd.RackHdException;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.request.UpdateFirmwareWorkflowRequest;
import com.dell.cpsd.component.rackhd.response.GetWorkflowResponse;
import com.dell.cpsd.component.rackhd.response.UpdateFirmwareWorkflowResponse;

import java.util.List;

/**
 * This is the Rack HD Installer Service interface. It is used for installing updates to various components discovered by Rack HD.
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
public interface RackHdInstallerService
{
    /**
     * Creates a Workflow in Rack HD. Returns Workflow Id if workflow successfully created.
     *
     * @param workflowRequest
     *            {@link UpdateFirmwareWorkflowRequest}
     * @param rackHdCriteria
     *            {@link RackHdCriteria}
     * @param nodeId
     *            {@link String}
     * @return {@link UpdateFirmwareWorkflowResponse}
     * @throws RackHdException when http exception or task exception occurs, while creating workflow
     * @since 1.0
     */
    UpdateFirmwareWorkflowResponse postWorkflowForNode(UpdateFirmwareWorkflowRequest workflowRequest, RackHdCriteria rackHdCriteria,
            String nodeId) throws RackHdException;

    /**
     * Gets list for Workflows associated with a node in Rack HD.
     *
     * @param nodeIdentifier
     *            {@link String}
     * @return {@link List} of {@link GetWorkflowResponse}
     * @throws RackHdException when http exception or task exception occurs, while getting workflow
     * @since 1.0
     */
    List<GetWorkflowResponse> getWorkflowsForNode(String nodeIdentifier) throws RackHdException;

    /**
     * Gets specific Workflow associated with a instance id in Rack HD.
     *
     * @param instanceId
     *            {@link String}
     * @param rackHdCriteria
     *            {@link RackHdCriteria}
     * @return {@link GetWorkflowResponse}
     * @throws RackHdException when http exception or task exception occurs, while getting workflow for an instance
     * @since 1.0
     */
    GetWorkflowResponse getWorkflow(String instanceId, RackHdCriteria rackHdCriteria) throws RackHdException;

}
