/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.service.impl;

import com.dell.cpsd.component.common.rest.CustomRestTemplate;
import com.dell.cpsd.component.rackhd.RackHdException;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.request.UpdateFirmwareWorkflowRequest;
import com.dell.cpsd.component.rackhd.response.AuthorizationResponse;
import com.dell.cpsd.component.rackhd.response.GetWorkflowResponse;
import com.dell.cpsd.component.rackhd.response.UpdateFirmwareWorkflowResponse;
import org.apache.log4j.Logger;
import org.hornetq.utils.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This is the RackHD Installer Service Implementation Test.
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved.
 * </p>
 *
 * @version 1.0
 * @since SINCE-TBD
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class RackHdInstallerServiceImplTest
{
    @Mock
    private CustomRestTemplate              restTemplate;

    private RackHdAuthenticationServiceImpl rackHdAuthenticationServiceImpl;
    private RackHdInstallerServiceImpl      rackHdInstallerServiceImpl;
    private RackHdCriteria                  rackHdCriteria;

    private static final Logger LOGGER = Logger.getLogger(RackHdInstallerServiceImpl.class);

    @Before
    public void setup() throws JSONException
    {
        rackHdCriteria = new RackHdCriteria("testHost", "testUser", "testPw", "testPort", true, null, "123", "/bla/bla.exe");

        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setMessage("TestMessage");
        authorizationResponse.setToken("testToken");

        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authorizationResponse,
                HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(AuthorizationResponse.class))).thenReturn(response);

        rackHdAuthenticationServiceImpl = new RackHdAuthenticationServiceImpl(restTemplate);
        rackHdInstallerServiceImpl = new RackHdInstallerServiceImpl(restTemplate, rackHdAuthenticationServiceImpl);
    }

    @Test
    public void getWorkflow() throws RackHdException
    {
        GetWorkflowResponse getWorkflowResponse = new GetWorkflowResponse();
        getWorkflowResponse.setDefinition("testDef");
        getWorkflowResponse.setDomain("testDomain");
        getWorkflowResponse.setId("testId");
        getWorkflowResponse.setInjectableName("testInjectableName");
        getWorkflowResponse.setInstanceId("testInstanceId");
        getWorkflowResponse.setName("testName");
        getWorkflowResponse.setNode("testNode");
        getWorkflowResponse.setServiceGraph("testServiceGraph");
        getWorkflowResponse.setStatus("testStatus");

        ResponseEntity<GetWorkflowResponse> response = new ResponseEntity<GetWorkflowResponse>(getWorkflowResponse, HttpStatus.ACCEPTED);
        when(restTemplate.getForEntity(anyString(), any(HttpHeaders.class), eq(GetWorkflowResponse.class))).thenReturn(response);

        GetWorkflowResponse workflow = rackHdInstallerServiceImpl.getWorkflow("testInstanceId", rackHdCriteria);

        Assert.assertEquals("testDef", workflow.getDefinition());
        Assert.assertEquals("testDomain", workflow.getDomain());
        Assert.assertEquals("testId", workflow.getId());
        Assert.assertEquals("testInjectableName", workflow.getInjectableName());
        Assert.assertEquals("testInstanceId", workflow.getInstanceId());
        Assert.assertEquals("testName", workflow.getName());
        Assert.assertEquals("testNode", workflow.getNode());
        Assert.assertEquals("testServiceGraph", workflow.getServiceGraph());
        Assert.assertEquals("testStatus", workflow.getStatus());
        verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(AuthorizationResponse.class));
        verify(restTemplate).getForEntity(anyString(), any(HttpHeaders.class), eq(GetWorkflowResponse.class));
    }

    @Test
    public void postWorkflowForNode() throws RackHdException
    {
        UpdateFirmwareWorkflowRequest updateFirmwareWorkflowRequest = new UpdateFirmwareWorkflowRequest("testUser", "testPw", "testPath");
        UpdateFirmwareWorkflowResponse updateFirmwareWorkflowResponse = new UpdateFirmwareWorkflowResponse();
        updateFirmwareWorkflowResponse.setCreatedAt("testCreatedAt");
        updateFirmwareWorkflowResponse.setDomain("testDomain");
        updateFirmwareWorkflowResponse.setInjectableName("testInjectableName");
        updateFirmwareWorkflowResponse.setInstanceId("testInstanceId");
        updateFirmwareWorkflowResponse.setName("testName");
        updateFirmwareWorkflowResponse.setNode("testNode");
        updateFirmwareWorkflowResponse.setStatus("testStatus");
        updateFirmwareWorkflowResponse.setUpdatedAt("testUpdatedAt");

        ResponseEntity<UpdateFirmwareWorkflowResponse> response = new ResponseEntity<UpdateFirmwareWorkflowResponse>(
                updateFirmwareWorkflowResponse, HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(UpdateFirmwareWorkflowResponse.class)))
                .thenReturn(response);

        try
        {
            UpdateFirmwareWorkflowResponse workflowForNode = rackHdInstallerServiceImpl
                    .postWorkflowForNode(updateFirmwareWorkflowRequest, rackHdCriteria, "testNodeId");
            Assert.assertEquals("testCreatedAt", workflowForNode.getCreatedAt());
            Assert.assertEquals("testDomain", workflowForNode.getDomain());
            Assert.assertEquals("testInjectableName", workflowForNode.getInjectableName());
            Assert.assertEquals("testInstanceId", workflowForNode.getInstanceId());
            Assert.assertEquals("testName", workflowForNode.getName());
            Assert.assertEquals("testNode", workflowForNode.getNode());
            Assert.assertEquals("testStatus", workflowForNode.getStatus());
            Assert.assertEquals("testUpdatedAt", workflowForNode.getUpdatedAt());
            verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(AuthorizationResponse.class));
            verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(UpdateFirmwareWorkflowResponse.class));
        }
        catch (HttpStatusCodeException hSCE) {
            LOGGER.error(hSCE.getStatusCode());
            LOGGER.error(hSCE.getResponseBodyAsString());
        }
    }

    @Test
    public void postWorkflowForNodeWithException() throws RackHdException
    {
        UpdateFirmwareWorkflowRequest updateFirmwareWorkflowRequest = new UpdateFirmwareWorkflowRequest("testUser", "testPw", "testPath");
        UpdateFirmwareWorkflowResponse updateFirmwareWorkflowResponse = new UpdateFirmwareWorkflowResponse();
        updateFirmwareWorkflowResponse.setCreatedAt("testCreatedAt");
        updateFirmwareWorkflowResponse.setDomain("testDomain");
        updateFirmwareWorkflowResponse.setInjectableName("testInjectableName");
        updateFirmwareWorkflowResponse.setInstanceId("testInstanceId");
        updateFirmwareWorkflowResponse.setName("testName");
        updateFirmwareWorkflowResponse.setNode("testNode");
        updateFirmwareWorkflowResponse.setStatus("testStatus");
        updateFirmwareWorkflowResponse.setUpdatedAt("testUpdatedAt");

        ResponseEntity<UpdateFirmwareWorkflowResponse> response = new ResponseEntity<UpdateFirmwareWorkflowResponse>(
                updateFirmwareWorkflowResponse, HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(UpdateFirmwareWorkflowResponse.class)))
                .thenThrow(Mockito.mock(HttpStatusCodeException.class));

        try
        {
            UpdateFirmwareWorkflowResponse workflowForNode = rackHdInstallerServiceImpl
                    .postWorkflowForNode(updateFirmwareWorkflowRequest, rackHdCriteria, "testNodeId");
            verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(AuthorizationResponse.class));
            verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(UpdateFirmwareWorkflowResponse.class));
        }
        catch (HttpStatusCodeException hSCE) {
            LOGGER.error(hSCE.getStatusCode());
            LOGGER.error(hSCE.getResponseBodyAsString());
        }
        catch (RackHdException rHE) {
            LOGGER.error(rHE.getMessage());
        }
    }

    @Test
    public void postWorkflowForNodeWithRestException() throws RackHdException
    {
        UpdateFirmwareWorkflowRequest updateFirmwareWorkflowRequest = new UpdateFirmwareWorkflowRequest("testUser", "testPw", "testPath");
        UpdateFirmwareWorkflowResponse updateFirmwareWorkflowResponse = new UpdateFirmwareWorkflowResponse();
        updateFirmwareWorkflowResponse.setCreatedAt("testCreatedAt");
        updateFirmwareWorkflowResponse.setDomain("testDomain");
        updateFirmwareWorkflowResponse.setInjectableName("testInjectableName");
        updateFirmwareWorkflowResponse.setInstanceId("testInstanceId");
        updateFirmwareWorkflowResponse.setName("testName");
        updateFirmwareWorkflowResponse.setNode("testNode");
        updateFirmwareWorkflowResponse.setStatus("testStatus");
        updateFirmwareWorkflowResponse.setUpdatedAt("testUpdatedAt");

        ResponseEntity<UpdateFirmwareWorkflowResponse> response = new ResponseEntity<UpdateFirmwareWorkflowResponse>(
                updateFirmwareWorkflowResponse, HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(UpdateFirmwareWorkflowResponse.class)))
                .thenThrow(Mockito.mock(RestClientException.class));

        try
        {
            UpdateFirmwareWorkflowResponse workflowForNode = rackHdInstallerServiceImpl
                    .postWorkflowForNode(updateFirmwareWorkflowRequest, rackHdCriteria, "testNodeId");
            verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(AuthorizationResponse.class));
            verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(UpdateFirmwareWorkflowResponse.class));
        }
        catch (RackHdException rHE) {
            LOGGER.error(rHE.getMessage());
        }
    }
}
