/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.client;

import com.beust.jcommander.Parameter;
import com.dell.cpsd.component.common.cli.CliParameters;
import com.dell.cpsd.component.common.client.validator.PathValidator;

/**
 * This is the RackHD Client Args Client class It contains Client CLI argument definitions Rack HD.
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
public class RackHDClientArgs implements CliParameters
{
    @Parameter(names = {"-i", "--ipAddress"}, descriptionKey = "PARAM_IP_ADDRESS")
    private String  ipAddress;

    @Parameter(names = {"-u", "--username"}, descriptionKey = "PARAM_USERNAME")
    private String  userName;

    @Parameter(names = {"-p", "--password"}, descriptionKey = "PARAM_PASSWORD")
    private String  password;

    @Parameter(names = {"-pt", "--port"}, descriptionKey = "PARAM_PORT")
    private String  port;

    @Parameter(names = {"-ih", "--isHttp"}, descriptionKey = "PARAM_IS_HTTP")
    private String  isHttp;

    @Parameter(names = {"-auth", "--authentication"}, descriptionKey = "USE_AUTH")
    private String auth;
    @Parameter(names = {"-f", "--outputFolder"}, descriptionKey = "PARAM_OUTPUT_FOLDER", validateWith = PathValidator.class)
    private String  outputFolder;

    @Parameter(names = {"-h", "--help"}, descriptionKey = "PARAM_HELP", help = true)
    private boolean help;

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    @Override
    public Boolean isHelp()
    {
        return help;
    }

    public void setHelp(boolean help)
    {
        this.help = help;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(final String port)
    {
        this.port = port;
    }

    public boolean isHttp()
    {
        return Boolean.parseBoolean(isHttp);
    }

    public void setHttp(final String http)
    {
        isHttp = http;
    }

    @Override
    public String getOutputFolder()
    {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder)
    {
        this.outputFolder = outputFolder;
    }

    public Boolean isAuth()
    {
        return Boolean.parseBoolean(auth);
    }

    public void setAuth(final String auth)
    {
        this.auth = auth;
    }
}
