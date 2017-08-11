/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd.service.impl;

import com.dell.cpsd.component.common.rest.CustomRestTemplate;
import com.dell.cpsd.component.rackhd.RackHdException;
import com.dell.cpsd.component.rackhd.criteria.RackHdCriteria;
import com.dell.cpsd.component.rackhd.enums.Endpoints;
import com.dell.cpsd.component.rackhd.enums.HttpConstantEnum;
import com.dell.cpsd.component.rackhd.response.AuthorizationResponse;
import com.dell.cpsd.component.rackhd.service.RackHdAuthenticationService;
import com.dell.cpsd.component.rackhd.utils.ApplicationUtil;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

/**
 * This is the RackHD Authentication ServiceImpl Service Implementation. It is used for authentication to Rack HD.
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
@Component
public class RackHdAuthenticationServiceImpl implements RackHdAuthenticationService
{
    private CustomRestTemplate restTemplate;

    private String             jwtToken;

    /**
     * Constructor for RackHdAuthenticationServiceImpl setting value for rest template
     *
     * @param restTemplate
     *            {@link CustomRestTemplate}
     */
    public RackHdAuthenticationServiceImpl(CustomRestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     */
    @Override
    public boolean login(final RackHdCriteria rackHdCriteria) throws RackHdException
    {
        try
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject json = new JSONObject();
            json.put(HttpConstantEnum.USERNAME.getValue(), rackHdCriteria.getUsername());
            json.put(HttpConstantEnum.PASSWORD.getValue(), rackHdCriteria.getPassword());

            HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

            String targetUrl = ApplicationUtil.formatEndpointUrl(rackHdCriteria, Endpoints.LOGIN);

            AuthorizationResponse authorizationResponse = restTemplate.postForEntity(targetUrl, request, AuthorizationResponse.class)
                    .getBody();

            this.jwtToken = HttpConstantEnum.JWT.getValue() + authorizationResponse.getToken();
        }
        catch (JSONException | RestClientException e)
        {
            throw new RackHdException(e);
        }

        return false;
    }

    /**
     * <p>
     * {@inheritDoc}
     * </p>
     */
    @Override
    public HttpHeaders authenticateAndCreateHeaders(final RackHdCriteria rackHdCriteria) throws RackHdException
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try
        {
            if (rackHdCriteria.isHttps())
            {
                login(rackHdCriteria);
                httpHeaders.set(HttpHeaders.AUTHORIZATION, jwtToken);
            }
            // Attempt to start separating Authentication from SSL
            // the above if statement will also use https and port 8443
            if ((rackHdCriteria.isAuth() != null) && rackHdCriteria.isAuth())
            {
                login(rackHdCriteria);
                httpHeaders.set(HttpHeaders.AUTHORIZATION, jwtToken);
            }
        }
        catch (RestClientException e)
        {
            throw new RackHdException(e);
        }

        return httpHeaders;
    }
}
