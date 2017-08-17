/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */
package com.dell.cpsd.component.rackhd.service.impl;

import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.dell.cpsd.component.common.rest.CustomRestTemplate;
import com.dell.cpsd.component.rackhd.RackHdException;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.enums.Endpoints;
import com.dell.cpsd.component.rackhd.enums.HttpConstantEnum;
import com.dell.cpsd.component.rackhd.response.AuthorizationResponse;
import com.dell.cpsd.component.rackhd.utils.ApplicationUtil;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This is the RackHD Authentication Service Implementation Test.
 * <p>
 * Dell EMC Confidential/Proprietary Information
 *
 * </p>
 * 
 * @version 1.0
 * 
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class RackHdAuthenticationServiceImplTest
{
    @Mock
    private CustomRestTemplate              restTemplate;

    private RackHdAuthenticationServiceImpl rackHdAuthenticationServiceImpl;
    private RackHdCriteria                  rackHdCriteria;

    @Before
    public void setup()
    {
        rackHdAuthenticationServiceImpl = new RackHdAuthenticationServiceImpl(restTemplate);
        rackHdCriteria = new RackHdCriteria("testHost", "testUser", "testPw", "testPort", true, null, "1234", "/bla/bla.exe");
    }

    @Test
    public void testCreate()
    {
        RackHdCriteria  rackHdCriteria = new RackHdCriteria("testHost", "testUser", "testPw", "testPort", true);

        Assert.assertNotNull(rackHdCriteria);
    }

    @Test
    public void testCreateWithAuth()
    {
        RackHdCriteria  rackHdCriteria = new RackHdCriteria("testHost", "testUser", "testPw", "testPort", true, true, "1234", "/bla/bla.exe");

        Assert.assertNotNull(rackHdCriteria);
        Assert.assertNotNull(rackHdCriteria.isAuth());
        Assert.assertNotNull(rackHdCriteria.getNodeId());
    }

    @Test(expected = NullPointerException.class)
    public void authenticateAndCreateHeaders_null() throws RackHdException
    {
        rackHdAuthenticationServiceImpl.authenticateAndCreateHeaders(rackHdCriteria);
    }

    @Test
    public void authenticateAndCreateHeaders() throws RackHdException, JSONException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject json = new JSONObject();
        json.put(HttpConstantEnum.USERNAME.getValue(), rackHdCriteria.getUsername());
        json.put(HttpConstantEnum.PASSWORD.getValue(), rackHdCriteria.getPassword());

        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

        String targetUrl = ApplicationUtil.formatEndpointUrl(rackHdCriteria, Endpoints.LOGIN);

        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setMessage("TestMessage");
        authorizationResponse.setToken("testToken");

        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authorizationResponse,
                HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(targetUrl, request, AuthorizationResponse.class)).thenReturn(response);
        HttpHeaders httpHeaders = rackHdAuthenticationServiceImpl.authenticateAndCreateHeaders(rackHdCriteria);

        Assert.assertTrue(httpHeaders.get("Authorization").contains("JWT testToken"));
        verify(restTemplate).postForEntity(targetUrl, request, AuthorizationResponse.class);
    }

    @Test
    public void authenticateAndCreateHeadersWithoutHttps() throws RackHdException, JSONException
    {
        rackHdCriteria = new RackHdCriteria("testHost", "testUser", "testPw", "testPort", false, true, "1234", "/bla/bla.exe");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject json = new JSONObject();
        json.put(HttpConstantEnum.USERNAME.getValue(), rackHdCriteria.getUsername());
        json.put(HttpConstantEnum.PASSWORD.getValue(), rackHdCriteria.getPassword());

        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

        String targetUrl = ApplicationUtil.formatEndpointUrl(rackHdCriteria, Endpoints.LOGIN);

        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setMessage("TestMessage");
        authorizationResponse.setToken("testToken");

        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authorizationResponse,
                HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(targetUrl, request, AuthorizationResponse.class)).thenReturn(response);
        HttpHeaders httpHeaders = rackHdAuthenticationServiceImpl.authenticateAndCreateHeaders(rackHdCriteria);

        Assert.assertTrue(httpHeaders.get("Authorization").contains("JWT testToken"));
        verify(restTemplate).postForEntity(targetUrl, request, AuthorizationResponse.class);
    }
}
