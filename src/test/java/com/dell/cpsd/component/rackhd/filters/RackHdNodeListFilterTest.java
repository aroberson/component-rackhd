/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.filters;

import java.util.List;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.dell.cpsd.component.rackhd.domain.model.Node;

/**
 * 
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class RackHdNodeListFilterTest
{

    RackHdNodeListFilter rackHdNodeListFilter;

    @Mock
    Node                 node1, node2, node3;

    @Before
    public void setup()
    {
        List<Node> nodeList = new ArrayList<Node>();
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        rackHdNodeListFilter = new RackHdNodeListFilter(nodeList);
    }

    @Test
    public void testRackHdNodeListFilter()
    {
        Assert.assertNotNull(rackHdNodeListFilter);
        Assert.assertNull(rackHdNodeListFilter.getFilterName());
        Assert.assertNull(rackHdNodeListFilter.getFilterType());
    }

    @Test
    public void testRackHdNodeListFilterWithoutNameType()
    {
        List<Node> nodeList = rackHdNodeListFilter.filter();
        Assert.assertNotNull(nodeList);
        Assert.assertEquals(3, nodeList.size());
    }

    @Test
    public void testRackHdNodeListFilterNameNoMatch()
    {
        Mockito.when(node1.getName()).thenReturn("testname1");
        Mockito.when(node2.getName()).thenReturn("testname2");
        Mockito.when(node3.getName()).thenReturn("testname3");

        rackHdNodeListFilter.setFilterName("testname");
        List<Node> nodeList = rackHdNodeListFilter.filter();
        Assert.assertEquals("testname", rackHdNodeListFilter.getFilterName());
        Assert.assertNull(nodeList);
    }

    @Test
    public void testRackHdNodeListFilterNameWithMatch()
    {
        Mockito.when(node1.getName()).thenReturn("testname1");
        Mockito.when(node2.getName()).thenReturn("testname2");
        Mockito.when(node3.getName()).thenReturn("testname3");

        rackHdNodeListFilter.setFilterName("testname1");
        List<Node> nodeList = rackHdNodeListFilter.filter();
        Assert.assertEquals("testname1", rackHdNodeListFilter.getFilterName());
        Assert.assertNotNull(nodeList);
        Assert.assertEquals(1, nodeList.size());
        Assert.assertEquals(node1, nodeList.get(0));
    }

    @Test
    public void testRackHdNodeListFilterNameMulitpleMatch()
    {
        Mockito.when(node1.getName()).thenReturn("testname1");
        Mockito.when(node2.getName()).thenReturn("testname1");
        Mockito.when(node3.getName()).thenReturn("testname3");

        rackHdNodeListFilter.setFilterName("testname1");
        List<Node> nodeList = rackHdNodeListFilter.filter();
        Assert.assertEquals("testname1", rackHdNodeListFilter.getFilterName());
        Assert.assertNotNull(nodeList);
        Assert.assertEquals(2, nodeList.size());
        Assert.assertEquals(node1, nodeList.get(0));
        Assert.assertEquals(node2, nodeList.get(1));
    }

    @Test
    public void testRackHdNodeListFilterTypeNoMatch()
    {
        Mockito.when(node1.getType()).thenReturn("test1");
        Mockito.when(node2.getType()).thenReturn("test2");
        Mockito.when(node3.getType()).thenReturn("test3");
        rackHdNodeListFilter.setFilterType("test");
        List<Node> nodeList = rackHdNodeListFilter.filter();
        Assert.assertEquals("test", rackHdNodeListFilter.getFilterType());
        Assert.assertNull(nodeList);
    }

    @Test
    public void testRackHdNodeListFilterTypeWithMatch()
    {
        Mockito.when(node1.getType()).thenReturn("test1");
        Mockito.when(node2.getType()).thenReturn("test2");
        Mockito.when(node3.getType()).thenReturn("test3");

        rackHdNodeListFilter.setFilterType("test2");

        List<Node> nodeList = rackHdNodeListFilter.filter();

        Assert.assertEquals("test2", rackHdNodeListFilter.getFilterType());
        Assert.assertNotNull(nodeList);
        Assert.assertEquals(1, nodeList.size());
        Assert.assertEquals(node2, nodeList.get(0));
    }

    @Test
    public void testRackHdNodeListFilterTypeMulitpleMatch()
    {
        Mockito.when(node1.getType()).thenReturn("test1");
        Mockito.when(node2.getType()).thenReturn("test1");
        Mockito.when(node3.getType()).thenReturn("test3");

        rackHdNodeListFilter.setFilterType("test1");
        List<Node> nodeList = rackHdNodeListFilter.filter();
        Assert.assertEquals("test1", rackHdNodeListFilter.getFilterType());
        Assert.assertNotNull(nodeList);
        Assert.assertEquals(2, nodeList.size());
        Assert.assertEquals(node1, nodeList.get(0));
        Assert.assertEquals(node2, nodeList.get(1));
    }

    @Test
    public void testRackHdNodeListFirstElementWithoutNameType()
    {
        Node node = rackHdNodeListFilter.filterFirstElement();
        Assert.assertNotNull(node);
        Assert.assertEquals(node1, node);
    }

    @Test
    public void testRackHdNodeListFirstElementNameNoMatch()
    {
        Mockito.when(node1.getName()).thenReturn("testname1");
        Mockito.when(node2.getName()).thenReturn("testname1");
        Mockito.when(node3.getName()).thenReturn("testname3");

        rackHdNodeListFilter.setFilterName("test");
        Node node = rackHdNodeListFilter.filterFirstElement();
        Assert.assertNull(node);
    }

    @Test
    public void testRackHdNodeListFirstElementNameMatch()
    {
        Mockito.when(node1.getName()).thenReturn("testname1");
        Mockito.when(node2.getName()).thenReturn("testname2");
        Mockito.when(node3.getName()).thenReturn("testname3");

        rackHdNodeListFilter.setFilterName("testname2");
        Node node = rackHdNodeListFilter.filterFirstElement();
        Assert.assertNotNull(node);
        Assert.assertEquals(node2, node);
    }

    @Test
    public void testRackHdNodeListFirstElementNameMultiMatch()
    {
        Mockito.when(node1.getName()).thenReturn("testname1");
        Mockito.when(node2.getName()).thenReturn("testname1");
        Mockito.when(node3.getName()).thenReturn("testname1");

        rackHdNodeListFilter.setFilterName("testname1");
        Node node = rackHdNodeListFilter.filterFirstElement();
        Assert.assertNotNull(node);
        Assert.assertEquals(node1, node);
    }

    @Test
    public void testRackHdNodeListFirstElementTypeNoMatch()
    {
        Mockito.when(node1.getType()).thenReturn("test1");
        Mockito.when(node2.getType()).thenReturn("test1");
        Mockito.when(node3.getType()).thenReturn("test3");

        rackHdNodeListFilter.setFilterType("test");
        Node node = rackHdNodeListFilter.filterFirstElement();
        Assert.assertNull(node);
    }

    @Test
    public void testRackHdNodeListFirstElementTypeMatch()
    {
        Mockito.when(node1.getType()).thenReturn("test1");
        Mockito.when(node2.getType()).thenReturn("test2");
        Mockito.when(node3.getType()).thenReturn("test3");

        rackHdNodeListFilter.setFilterType("test2");
        Node node = rackHdNodeListFilter.filterFirstElement();
        Assert.assertNotNull(node);
        Assert.assertEquals(node2, node);
    }

    @Test
    public void testRackHdNodeListFirstElementTypeMultiMatch()
    {
        Mockito.when(node1.getType()).thenReturn("test1");
        Mockito.when(node2.getType()).thenReturn("test1");
        Mockito.when(node3.getType()).thenReturn("test1");

        rackHdNodeListFilter.setFilterType("test1");
        Node node = rackHdNodeListFilter.filterFirstElement();
        Assert.assertNotNull(node);
        Assert.assertEquals(node1, node);
    }
}
