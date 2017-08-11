/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.criteria;

/**
 * This is the RackHD Criteria class It contains information for connecting to Rack HD.
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
public class RackHdCriteria
{
    private String  hostname;

    private String  username;

    private String  password;

    private String  port;

    private boolean isHttps;

    private Boolean isAuth;

    /**
     *
     * @param hostname
     * @param username
     * @param password
     * @param port
     * @param isHttps
     */
    public RackHdCriteria(
            final String hostname,
            final String username,
            final String password,
            final String port,
            final boolean isHttps)
    {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.isHttps = isHttps;
    }

    /**
     *
     * @param hostname
     * @param username
     * @param password
     * @param port
     * @param isHttps
     * @param isAuth
     */
    public RackHdCriteria(
            final String hostname,
            final String username,
            final String password,
            final String port,
            final boolean isHttps,
            final Boolean isAuth)
    {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
        this.isHttps = isHttps;
        this.isAuth = isAuth;
    }

    public String getHostname()
    {
        return hostname;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPort()
    {
        return port;
    }

    public boolean isHttps()
    {
        return isHttps;
    }

    public Boolean isAuth() {return isAuth;}
}
