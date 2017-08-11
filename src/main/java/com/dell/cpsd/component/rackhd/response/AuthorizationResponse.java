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
public class AuthorizationResponse
{
    @JsonProperty("token")
    private String token;

    @JsonProperty("message")
    private String message;

    public String getToken()
    {
        return token;
    }

    public void setToken(final String token)
    {
        this.token = token;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }
}
