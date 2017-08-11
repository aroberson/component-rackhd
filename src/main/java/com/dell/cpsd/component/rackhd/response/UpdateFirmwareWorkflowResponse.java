/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the UpdateFirmwareWorkflowResponse class It contains Response information from Update Firmware Workflow to Rack HD.
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
public class UpdateFirmwareWorkflowResponse
{
    @JsonProperty("instanceId")
    private String instanceId;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("name")
    private String name;

    @JsonProperty("injectableName")
    private String injectableName;

    @JsonProperty("_status")
    private String status;

    @JsonProperty("node")
    private String node;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("createdAt")
    private String createdAt;

    public String getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(final String instanceId)
    {
        this.instanceId = instanceId;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(final String domain)
    {
        this.domain = domain;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getInjectableName()
    {
        return injectableName;
    }

    public void setInjectableName(final String injectableName)
    {
        this.injectableName = injectableName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(final String status)
    {
        this.status = status;
    }

    public String getNode()
    {
        return node;
    }

    public void setNode(final String node)
    {
        this.node = node;
    }

    public String getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(final String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt)
    {
        this.createdAt = createdAt;
    }
}
