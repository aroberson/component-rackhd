/**
 * Copyright © 2016 Dell Inc. or its subsidiaries. All Rights Reserved 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * This is the Node It contains Node information form Rack HD.
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node
{
    @JsonProperty
    private boolean                   autoDiscover;

    @JsonProperty
    private String                    catalogs;

    @JsonProperty
    private String                    id;

    @JsonProperty
    private List<Object>              identifiers;

    @JsonProperty
    private String                    name;

    @JsonProperty
    private List<Map<String, Object>> obms;

    @JsonProperty
    private String                    tags;

    @JsonProperty
    private String                    pollers;

    @JsonProperty
    private List<Map<String, Object>> relations;

    @JsonProperty
    private String                    sku;

    @JsonProperty
    private String                    type;

    @JsonProperty
    private String                    workflows;

    private List<Catalog>             catalogList;

    public List<Catalog> getCatalogList()
    {
        return catalogList;
    }

    public void setCatalogList(List<Catalog> catalogList)
    {
        this.catalogList = catalogList;
    }

    public String getId()
    {
        return id;
    }

    public boolean isAutoDiscover()
    {
        return autoDiscover;
    }

    public String getCatalogs()
    {
        return catalogs;
    }

    public List<Object> getIdentifiers()
    {
        return identifiers;
    }

    public String getName()
    {
        return name;
    }

    public List<Map<String, Object>> getObms()
    {
        return obms;
    }

    public String getTags()
    {
        return tags;
    }

    public String getPollers()
    {
        return pollers;
    }

    public List<Map<String, Object>> getRelations()
    {
        return relations;
    }

    public String getSku()
    {
        return sku;
    }

    public String getType()
    {
        return type;
    }

    public String getWorkflows()
    {
        return workflows;
    }
}
