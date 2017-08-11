/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.service;

import com.dell.cpsd.component.rackhd.RackHdException;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import org.springframework.http.HttpHeaders;

/**
 * This is the Rack HD Authentication Service interface. It is used for authentication to Rack HD.
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
public interface RackHdAuthenticationService
{
    boolean login(RackHdCriteria rackHdCriteria) throws RackHdException;

    HttpHeaders authenticateAndCreateHeaders(RackHdCriteria rackHdCriteria) throws RackHdException;
}
