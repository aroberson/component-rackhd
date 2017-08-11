/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.config;

import com.dell.cpsd.component.rackhd.client.RackHDClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is the configuration for the CLI Client.
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 * 
 * @version 1.0
 * 
 * @since 1.0
 */
@Configuration
public class ClientConfig
{
    /**
     * Returns Rack HD Client
     *
     * @return {@link RackHDClient}.
     * @since 1.0
     */
    @Bean
    @Qualifier("rackHdClient")
    RackHDClient rackHdClient()
    {
        return new RackHDClient();
    }
}
