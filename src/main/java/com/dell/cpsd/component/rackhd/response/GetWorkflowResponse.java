/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the AuthorizationResponse class It contains Response information from login to Rack HD.
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
public class GetWorkflowResponse
{
    @JsonProperty("node")
    private String node;

    @JsonProperty("status")
    private String status;

    @JsonProperty("definition")
    private String definition;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("id")
    private String id;

    @JsonProperty("injectableName")
    private String injectableName;

    @JsonProperty("instanceId")
    private String instanceId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("serviceGraph")
    private String serviceGraph;

    public String getNode()
    {
        return node;
    }

    public void setNode(final String node)
    {
        this.node = node;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(final String status)
    {
        this.status = status;
    }

    public String getDefinition()
    {
        return definition;
    }

    public void setDefinition(final String definition)
    {
        this.definition = definition;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(final String domain)
    {
        this.domain = domain;
    }

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public String getInjectableName()
    {
        return injectableName;
    }

    public void setInjectableName(final String injectableName)
    {
        this.injectableName = injectableName;
    }

    public String getInstanceId()
    {
        return instanceId;
    }

    public void setInstanceId(final String instanceId)
    {
        this.instanceId = instanceId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getServiceGraph()
    {
        return serviceGraph;
    }

    public void setServiceGraph(final String serviceGraph)
    {
        this.serviceGraph = serviceGraph;
    }
}
