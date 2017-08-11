/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the SKU class It contains SKU information specific to Rack HD.
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
public class Sku
{
    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    @JsonProperty("workflowRoot")
    private String workflowRoot;

    @JsonProperty("discoveryGraphName")
    private String discoveryGraphName;

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public String getWorkflowRoot()
    {
        return workflowRoot;
    }

    public void setWorkflowRoot(final String workflowRoot)
    {
        this.workflowRoot = workflowRoot;
    }

    public String getDiscoveryGraphName()
    {
        return discoveryGraphName;
    }

    public void setDiscoveryGraphName(final String discoveryGraphName)
    {
        this.discoveryGraphName = discoveryGraphName;
    }
}
