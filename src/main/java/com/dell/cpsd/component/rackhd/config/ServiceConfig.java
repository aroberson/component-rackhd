/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.config;

import com.dell.cpsd.component.common.rest.CustomRestTemplate;
import com.dell.cpsd.component.rackhd.service.RackHdAuthenticationService;
import com.dell.cpsd.component.rackhd.service.RackHdDiscoveryService;
import com.dell.cpsd.component.rackhd.service.RackHdInstallerService;
import com.dell.cpsd.component.rackhd.service.impl.RackHdAuthenticationServiceImpl;
import com.dell.cpsd.component.rackhd.service.impl.RackHdDiscoveryServiceImpl;
import com.dell.cpsd.component.rackhd.service.impl.RackHdInstallerServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is the configuration for the Service Interfaces.
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
@Configuration
public class ServiceConfig
{
    /**
     * Returns new instance of Rack HD Authentication service.
     *
     * @return {@link RackHdDiscoveryService}The release matrix data service.
     * @since 1.0
     *//*
       * @Bean
       * @Qualifier("rackHdAuthenticationService") CustomRestTemplate rackHdAuthenticationService() { return new
       * RackHdAuthenticationServiceImpl(new CustomRestTemplate()); }
       */

    /**
     * Returns new instance of Rack HD Authentication service.
     *
     * @return {@link RackHdDiscoveryService}The release matrix data service.
     * @since 1.0
     */
    @Bean
    @Qualifier("rackHdAuthenticationService")
    RackHdAuthenticationService rackHdAuthenticationService()
    {
        return new RackHdAuthenticationServiceImpl(new CustomRestTemplate());
    }

    /**
     * Returns new instance of Rack HD Discovery service.
     *
     * @return {@link RackHdDiscoveryService}The release matrix data service.
     * @since 1.0
     */
    @Bean
    @Qualifier("rackHdDiscoveryService")
    RackHdDiscoveryService rackHdDiscoveryService()
    {
        return new RackHdDiscoveryServiceImpl(new CustomRestTemplate(), rackHdAuthenticationService());
    }

    /**
     * Returns new instance of Rack HD Installer service.
     *
     * @return {@link RackHdDiscoveryService}The release matrix data service.
     * @since 1.0
     */
    @Bean
    @Qualifier("rackHdInstallerService")
    RackHdInstallerService rackHdInstallerService()
    {
        return new RackHdInstallerServiceImpl(new CustomRestTemplate(), rackHdAuthenticationService());
    }
}
