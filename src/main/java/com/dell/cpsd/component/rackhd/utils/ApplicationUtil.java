/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.utils;

import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.enums.Endpoints;
import com.dell.cpsd.component.rackhd.enums.HttpConstantEnum;

/**
 * This is the Application Utility class
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

public class ApplicationUtil
{
    /**
     * This method creates endpoint url for RackHD http requests . url = protocol:// + hostname + : + port + / + endpoint. Formats endpoint
     * string by replacing placeholders with params
     *
     * @param rackHdCriteria
     *            {@link RackHdCriteria}
     * @param endpoints
     *            {@link Endpoints}
     * @param param
     *            {@link String}
     * @return {@link String }
     */
    public static String formatEndpointUrl(final RackHdCriteria rackHdCriteria, final Endpoints endpoints, String... param)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(rackHdCriteria.isHttps() ? HttpConstantEnum.HTTPS.getValue() : HttpConstantEnum.HTTP.getValue())
                .append(rackHdCriteria.getHostname()).append(HttpConstantEnum.COLON.getValue()).append(rackHdCriteria.getPort())
                .append(String.format(endpoints.getValue(), param));

        return builder.toString();
    }
}