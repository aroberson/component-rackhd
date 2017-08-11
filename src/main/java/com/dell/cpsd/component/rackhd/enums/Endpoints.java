/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.enums;

/**
 * This is the Endpoints enum for the RackHD service. Provides all the endpoint urls for RackHD
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
public enum Endpoints
{
    LOGIN("/login"),
    API_2_0("/api/2.0/"),
    UPDATE_FIRMWARE_V2(API_2_0.getValue() + "nodes/%s/workflows?name=Graph.Dell.Racadm.Update.Firmware"),
    WORKFLOWS_V2(API_2_0.getValue() + "workflows/%s"),
    NODE_V2(API_2_0.getValue() + "nodes/"),
    CATALOGS_V2(API_2_0.getValue() + "catalogs/"),
    NODE_CATALOGS_V2(API_2_0.getValue() + "nodes/%s/catalogs"),
    NODE_CATALOGS_DMI_V2(API_2_0.getValue() + "nodes/%s/catalogs/dmi");

    private final String endpoint;

    Endpoints(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public String getValue()
    {
        return this.endpoint;
    }
}
