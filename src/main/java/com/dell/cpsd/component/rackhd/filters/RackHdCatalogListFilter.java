/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.filters;

import com.dell.cpsd.component.rackhd.domain.model.Catalog;
import org.apache.log4j.Logger;

import java.util.ArrayList;
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

public class RackHdCatalogListFilter extends AbstractFilter<Catalog>
{

    private static final Logger LOGGER = Logger.getLogger(RackHdCatalogListFilter.class);

    private String              filterSource;

    public RackHdCatalogListFilter(List<Catalog> listToFilter)
    {
        super(listToFilter);
    }

    /**
     * Perform the filter based on the passed Catalog contained in filterSource If more than one result is returned only the first entry in
     * the list is returned.
     *
     */
    @Override
    public Catalog filterFirstElement()
    {
        if (filterSource != null)
        {
            listToFilter = doFilterBySource(filterSource);
        }

        if ((listToFilter == null) || (listToFilter.isEmpty()))
        {
            return null;
        }

        else if (listToFilter.size() >= 2)
        {
            LOGGER.warn("more than one result with source: " + filterSource + ". Taking first entry.");
        }

        return listToFilter.get(0);
    }

    /**
     * Perform the filter based on the passed Catalog contained in filterSource If more than one result is returned the list of catalogs.
     *
     */
    @Override
    public List<Catalog> filter()
    {
        if (filterSource != null)
        {
            listToFilter = doFilterBySource(filterSource);
        }

        if ((listToFilter == null) || (listToFilter.isEmpty()))
        {
            return null;
        }

        return listToFilter;
    }

    /**
     * Create the list of catalogs based on the passed source catalog string
     *
     */
    private List<Catalog> doFilterBySource(String source)
    {
        List<Catalog> catalogs = new ArrayList<>();
        for (Catalog n : listToFilter)
        {
            if (n.getSource().equals(source))
            {
                if (!catalogs.isEmpty())
                {
                    LOGGER.debug("more than one Catalog with name: " + source);
                }
                catalogs.add(n);
            }
        }
        return catalogs;
    }

    public String getFilterSource()
    {
        return filterSource;
    }

    public void setFilterSource(String filterSource)
    {
        this.filterSource = filterSource;
    }
}
