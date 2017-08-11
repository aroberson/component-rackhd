/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.service;

import com.dell.cpsd.component.common.exception.ComponentServicesException;
import com.dell.cpsd.component.common.validator.ValidationResult;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.domain.model.RackHDModel;

/**
 * This is the Rack HD Discovery Service interface. It is used for discovering to various components using Rack HD.
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
public interface RackHdDiscoveryService
{
    RackHDModel discover(RackHdCriteria rackHDCriteria) throws ComponentServicesException;

    ValidationResult validate(RackHdCriteria rackHDCriteria);
}
