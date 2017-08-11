/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.service.impl;

import com.dell.cpsd.component.common.rest.CustomRestTemplate;
import com.dell.cpsd.component.common.validator.ValidationResult;
import com.dell.cpsd.component.rackhd.RackHdException;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.domain.model.Catalog;
import com.dell.cpsd.component.rackhd.domain.model.Node;
import com.dell.cpsd.component.rackhd.domain.model.RackHDModel;
import com.dell.cpsd.component.rackhd.enums.Endpoints;
import com.dell.cpsd.component.rackhd.response.AuthorizationResponse;
import com.dell.cpsd.component.rackhd.utils.ApplicationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hornetq.utils.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * This is the RackHD Discovery Service Implementation Test.
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved.
 * </p>
 *
 * @version 1.0
 * @since SINCE-TBD
 */
@RunWith(MockitoJUnitRunner.class)
public class RackHdDiscoveryServiceImplTest
{
    @Mock
    private CustomRestTemplate              restTemplate;

    private RackHdAuthenticationServiceImpl rackHdAuthenticationServiceImpl;
    private RackHdDiscoveryServiceImpl      rackHdDiscoveryServiceImpl;
    private RackHdCriteria                  rackHdCriteria;

    @Before
    public void setup() throws JSONException
    {
        rackHdCriteria = new RackHdCriteria("testHost", "testUser", "testPw", "testPort", true, null);

        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setMessage("TestMessage");
        authorizationResponse.setToken("testToken");

        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authorizationResponse,
                HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(AuthorizationResponse.class))).thenReturn(response);

        rackHdAuthenticationServiceImpl = new RackHdAuthenticationServiceImpl(restTemplate);
        rackHdDiscoveryServiceImpl = new RackHdDiscoveryServiceImpl(restTemplate, rackHdAuthenticationServiceImpl);
    }

    @Test
    public void discover() throws RackHdException, JsonProcessingException, IOException
    {
        ObjectMapper nodeMapper = new ObjectMapper();
        JsonNode nodeData = nodeMapper
                .readTree("{\"autoDiscover\": false, \"catalogs\": \"testCatalog\", \"id\": \"testNodeId\", \"identifiers\": [\"testIden\"], \"name\": \"testName\", \"obms\": [], \"pollers\": \"testPollers\", \"relations\": [], \"sku\": \"565cb91669aa70ab450da9dd\", \"tags\": \"testTag\", \"type\": \"testType\", \"workflows\": \"\"}");
        ObjectMapper catalogMapper = new ObjectMapper();
        JsonNode catalogData = catalogMapper
                .readTree("{\"id\": \"testCatalogId\", \"node\": \"testNodeId\", \"source\": \"testSrc\", \"data\": []}");

        Node testNode = nodeMapper.treeToValue(nodeData, Node.class);
        Catalog testCatalog = catalogMapper.treeToValue(catalogData, Catalog.class);
        List<Catalog> catalogBody = Arrays.asList(testCatalog);
        List<Node> nodeBody = Arrays.asList(testNode);
        ResponseEntity<List<Catalog>> catalogResponse = new ResponseEntity<List<Catalog>>(catalogBody, HttpStatus.ACCEPTED);
        ResponseEntity<List<Node>> nodeResponse = new ResponseEntity<List<Node>>(nodeBody, HttpStatus.ACCEPTED);

        when(
                restTemplate.exchange(ApplicationUtil.formatEndpointUrl(rackHdCriteria, Endpoints.CATALOGS_V2), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Catalog>>()
                        {
                        })).thenReturn(catalogResponse);

        when(
                restTemplate.exchange(ApplicationUtil.formatEndpointUrl(rackHdCriteria, Endpoints.NODE_V2), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Node>>()
                        {
                        })).thenReturn(nodeResponse);

        RackHDModel rackHDModel = rackHdDiscoveryServiceImpl.discover(rackHdCriteria);
        Assert.assertTrue(rackHDModel.getNodes().contains(testNode));
    }

    @Test(expected = com.google.common.util.concurrent.UncheckedExecutionException.class)
    public void discover_null() throws RackHdException
    {
        rackHdDiscoveryServiceImpl.discover(rackHdCriteria);
    }

    @Test
    public void validate()
    {
        ValidationResult validationResult = rackHdDiscoveryServiceImpl.validate(rackHdCriteria);
        Assert.assertEquals("RackHdDiscoveryService", validationResult.getKey());
        Assert.assertTrue(validationResult.isSuccess());
    }

    @Test
    public void validate_null()
    {
        ValidationResult validationResult = rackHdDiscoveryServiceImpl.validate(null);
        Assert.assertEquals("RackHdDiscoveryService", validationResult.getKey());
        Assert.assertFalse(validationResult.isSuccess());
    }
}
