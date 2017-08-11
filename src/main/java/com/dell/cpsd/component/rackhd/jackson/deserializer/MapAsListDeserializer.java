/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This is MapAsListDeserializer. It is used to deserialize json to List of Map of key and object.
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
public class MapAsListDeserializer extends JsonDeserializer<List<Map<String, Object>>>
{
    protected MapAsListDeserializer()
    {
        super();
    }

    @Override
    public List<Map<String, Object>> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
    {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        if (node.isArray() && !node.elements().hasNext())
        {
            return null;
        }
        else if (node.isObject())
        {
            ArrayNode jsonNodes = new ArrayNode(JsonNodeFactory.instance);
            jsonNodes.add(node);
            return oc.treeToValue(jsonNodes, List.class);
        }
        return oc.treeToValue(node, List.class);
    }
}
