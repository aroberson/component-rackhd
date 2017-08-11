/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */


package com.dell.cpsd.component.rackhd.filters;

import com.dell.cpsd.component.rackhd.domain.model.Node;
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
public class RackHdNodeListFilter extends AbstractFilter<Node>
{
    /*
     * The reference to a logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(RackHdNodeListFilter.class);

    private String              filterName;
    private String              filterType;

    public RackHdNodeListFilter(List<Node> nodeList)
    {
        super(nodeList);
    }

    /**
     * @param name
     * @return
     */
    private List<Node> doFilterByName(String name)
    {
        List<Node> nodes = new ArrayList<>();
        for (Node n : listToFilter)
        {
            if (n.getName().equals(name))
            {
                if (!nodes.isEmpty())
                {
                    LOGGER.debug("more than one Node with name: " + name);
                }
                nodes.add(n);
            }
        }
        return nodes;
    }

    /**
     * @param type
     * @return
     */
    private List<Node> doFilterByType(String type)
    {
        List<Node> nodes = new ArrayList<>();
        for (Node n : listToFilter)
        {
            if (n.getType().equals(type))
            {
                if (!nodes.isEmpty())
                {
                    LOGGER.debug("more than one Node of type: " + type);
                }
                nodes.add(n);
            }
        }
        return nodes;
    }

    @Override
    public Node filterFirstElement()
    {
        if (filterName != null)
        {
            listToFilter = doFilterByName(filterName);
        }

        if (filterType != null)
        {
            listToFilter = doFilterByType(filterType);
        }

        if ((listToFilter == null) || (listToFilter.isEmpty()))
        {
            return null;
        }

        return listToFilter.get(0);
    }

    @Override
    public List<Node> filter()
    {
        if (filterName != null)
        {
            listToFilter = doFilterByName(filterName);
        }

        if (filterType != null)
        {
            listToFilter = doFilterByType(filterType);
        }

        if ((listToFilter == null) || (listToFilter.isEmpty()))
        {
            return null;
        }

        return listToFilter;
    }

    public String getFilterName()
    {
        return filterName;
    }

    public void setFilterName(String filterName)
    {
        this.filterName = filterName;
    }

    public String getFilterType()
    {
        return filterType;
    }

    public void setFilterType(String filterType)
    {
        this.filterType = filterType;
    }
}
