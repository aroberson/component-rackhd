/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.filters;

import com.dell.cpsd.component.rackhd.domain.model.Catalog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RackHdCatalogListFilterTest
{

    RackHdCatalogListFilter rackHdCatalogListFilter;

    @Mock
    Catalog                 catalog1, catalog2, catalog3;

    @Before
    public void setup()
    {
        List<Catalog> catalogList = new ArrayList<Catalog>();
        catalogList.add(catalog1);
        catalogList.add(catalog2);
        catalogList.add(catalog3);
        rackHdCatalogListFilter = new RackHdCatalogListFilter(catalogList);
    }

    @Test
    public void testRackHdCatalogListFilter()
    {
        Assert.assertNotNull(rackHdCatalogListFilter);
    }

    @Test
    public void testFilterWithNullSource()
    {
        rackHdCatalogListFilter.listToFilter = null;
        rackHdCatalogListFilter.setFilterSource(null);
        Assert.assertNull(rackHdCatalogListFilter.filter());
        Assert.assertNull(rackHdCatalogListFilter.getFilterSource());
        Assert.assertNull(rackHdCatalogListFilter.filterFirstElement());
    }

    @Test
    public void testFilterListWithoutSource()
    {
        rackHdCatalogListFilter.setFilterSource(null);

        List<Catalog> filteredList = rackHdCatalogListFilter.filter();
        Assert.assertNotNull(rackHdCatalogListFilter.filter());
        Assert.assertEquals(3, filteredList.size());
    }

    @Test
    public void testFilterListWithSource()
    {
        Mockito.when(catalog1.getSource()).thenReturn("test1");
        Mockito.when(catalog2.getSource()).thenReturn("test2");
        Mockito.when(catalog3.getSource()).thenReturn("test3");
        Assert.assertNull(catalog1.getId());
        Assert.assertNull(catalog1.getNode());
        Assert.assertNull(catalog1.getNodeId());

        rackHdCatalogListFilter.setFilterSource("test1");

        List<Catalog> filteredList = rackHdCatalogListFilter.filter();
        Assert.assertNotNull(rackHdCatalogListFilter.filter());
        Assert.assertEquals("test1", rackHdCatalogListFilter.getFilterSource());
        Assert.assertEquals(1, filteredList.size());
        Assert.assertEquals(catalog1, filteredList.get(0));
    }

    @Test
    public void testFilterListWithMultipleMatches()
    {
        Mockito.when(catalog1.getSource()).thenReturn("test1");
        Mockito.when(catalog2.getSource()).thenReturn("test1");
        Mockito.when(catalog3.getSource()).thenReturn("test3");

        rackHdCatalogListFilter.setFilterSource("test1");

        List<Catalog> filteredList = rackHdCatalogListFilter.filter();
        Assert.assertNotNull(rackHdCatalogListFilter.filter());
        Assert.assertEquals(2, filteredList.size());
        Assert.assertEquals(catalog1, filteredList.get(0));
        Assert.assertEquals(catalog2, filteredList.get(1));
    }

    @Test
    public void testFilterFirstElementWithoutSource()
    {
        rackHdCatalogListFilter.setFilterSource(null);

        Catalog filteredFirstElement = rackHdCatalogListFilter.filterFirstElement();
        Assert.assertNotNull(filteredFirstElement);
        Assert.assertEquals(catalog1, filteredFirstElement);
    }

    // If Catalog is not initialized or has null source, this code throws NPE
    @Test
    public void testFilterFirstElementWithSource()
    {
        Mockito.when(catalog1.getSource()).thenReturn("test1");
        Mockito.when(catalog2.getSource()).thenReturn("test2");
        Mockito.when(catalog3.getSource()).thenReturn("test3");

        rackHdCatalogListFilter.setFilterSource("test2");

        Catalog filteredFirstElement = rackHdCatalogListFilter.filterFirstElement();
        Assert.assertNotNull(filteredFirstElement);
        Assert.assertEquals(catalog2, filteredFirstElement);
    }

    @Test
    public void testFilterFirstElementWithMultipleMatches()
    {
        Mockito.when(catalog1.getSource()).thenReturn("test1");
        Mockito.when(catalog2.getSource()).thenReturn("test1");
        Mockito.when(catalog3.getSource()).thenReturn("test3");

        rackHdCatalogListFilter.setFilterSource("test1");

        Catalog filteredFirstElement = rackHdCatalogListFilter.filterFirstElement();
        Assert.assertNotNull(filteredFirstElement);
        Assert.assertEquals(catalog1, filteredFirstElement);
    }

}
