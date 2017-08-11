/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.enums;

/**
 * This is inner String Constant Enum for this class.
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
public enum HttpConstantEnum
{
    JWT("JWT "),
    HTTPS("https://"),
    AUTH("auth"),
    HTTP("http://"),
    USERNAME("username"),
    PASSWORD("password"),
    COLON(":");

    private final String httpConstantEnum;

    HttpConstantEnum(String httpConstantEnum)
    {
        this.httpConstantEnum = httpConstantEnum;
    }

    public String getValue()
    {
        return this.httpConstantEnum;
    }
}