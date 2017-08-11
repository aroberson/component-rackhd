/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.domain.model;

import com.dell.cpsd.component.rackhd.jackson.deserializer.MapAsListDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Map;

/**
 * This is the Catalog It contains Catalog information from Rack HD.
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
public class Catalog
{
    @JsonProperty
    private String                    id;

    @JsonProperty
    private String                    node;

    private String                    nodeId;

    @JsonDeserialize(using = MapAsListDeserializer.class)
    private List<Map<String, Object>> data;

    @JsonProperty
    private String                    source;

    public String getNode()
    {
        return node;
    }

    public String getNodeId()
    {
        if (node == null)
        {
            return null;
        }
        else if (nodeId != null)
        {
            return nodeId;
        }
        int nodeStart = node.lastIndexOf('/');
        if (nodeStart == -1)
        {
            return null;
        }
        nodeId = node.substring(nodeStart + 1);
        return nodeId;
    }

    public List<Map<String, Object>> getData()
    {
        return data;
    }

    public String getId()
    {
        return id;
    }

    public String getSource()
    {
        return source;
    }
}
