/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.filters;

import java.util.List;

/**
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
public abstract class AbstractFilter<T>
{
    protected List<T> listToFilter;

    public AbstractFilter(List<T> listToFilter)
    {
        this.listToFilter = listToFilter;
    }

    public abstract T filterFirstElement();

    public abstract List<T> filter();
}
