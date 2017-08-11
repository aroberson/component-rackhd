/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the UpdateFirmwareWorkflowRequest class It contains Request information for update firmware on Rack HD.
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
public class UpdateFirmwareWorkflowRequest
{
    @JsonProperty("serverUsername")
    private String serverUsername;

    @JsonProperty("serverPassword")
    private String serverPassword;

    @JsonProperty("serverFilePath")
    private String serverFilePath;

    public UpdateFirmwareWorkflowRequest(final String serverUsername, final String serverPassword, final String serverFilePath)
    {
        this.serverUsername = serverUsername;
        this.serverPassword = serverPassword;
        this.serverFilePath = serverFilePath;
    }

    public String getServerUsername()
    {
        return serverUsername;
    }

    public String getServerPassword()
    {
        return serverPassword;
    }

    public String getServerFilePath()
    {
        return serverFilePath;
    }
}
