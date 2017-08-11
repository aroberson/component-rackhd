/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
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
import com.dell.cpsd.component.rackhd.service.RackHdAuthenticationService;
import com.dell.cpsd.component.rackhd.service.RackHdDiscoveryService;
import com.dell.cpsd.component.rackhd.utils.ApplicationUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * This is the RackHdDiscoveryServiceImpl class. It is the implementation of RackHdDiscoveryService. It is used for discovering various
 * nodes and components from RackHD
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
public class RackHdDiscoveryServiceImpl implements RackHdDiscoveryService
{
    private CustomRestTemplate                              restTemplate;
    private final LoadingCache<RackHdCriteria, RackHDModel> SERVICE_CACHE = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<RackHdCriteria, RackHDModel>()
                                                                                  {
                                                                                      public RackHDModel load(RackHdCriteria key)
                                                                                              throws RackHdException
                                                                                      {
                                                                                          return discoverIntoCache(key);
                                                                                      }
                                                                                  });
    private RackHdAuthenticationService                     rackHdAuthenticationService;

    public RackHdDiscoveryServiceImpl(CustomRestTemplate template, RackHdAuthenticationService rackHdAuthenticationService)
    {
        this.restTemplate = template;
        this.rackHdAuthenticationService = rackHdAuthenticationService;
    }

    @Override
    public RackHDModel discover(RackHdCriteria rackHDCriteria) throws RackHdException
    {
        try
        {
            return SERVICE_CACHE.get(rackHDCriteria);
        }
        catch (ExecutionException e)
        {
            throw new RackHdException(e);
        }
    }

    @Override
    public ValidationResult validate(RackHdCriteria rackHDCriteria)
    {
        ValidationResult validationResult = new ValidationResult("RackHdDiscoveryService");
        try
        {
            loginAndSetDefaultHeaders(rackHDCriteria);

            validationResult.setSuccess(true);
            return validationResult;
        }
        catch (Exception e)
        {
            validationResult.addMessage(e.toString());
            validationResult.addStackTrace(Arrays.asList(e.getStackTrace()));
            validationResult.setSuccess(false);
            return validationResult;
        }
    }

    private RackHDModel discoverIntoCache(RackHdCriteria rackHDCriteria) throws RackHdException
    {
        List<Node> nodeList = discoverNodes(rackHDCriteria);
        List<Catalog> catalogList = discoverCatalogs(rackHDCriteria);

        buildHierarchy(nodeList, catalogList);

        RackHDModel rHDm = new RackHDModel();
        rHDm.setNodes(nodeList);
        return rHDm;
    }

    private void buildHierarchy(List<Node> nodeList, List<Catalog> catalogList)
    {
        if (nodeList == null || catalogList == null)
        {
            return;
        }
        for (Node n : nodeList)
        {
            List<Catalog> children = new ArrayList<>();
            Iterator<Catalog> iterator = catalogList.iterator();
            for (; iterator.hasNext();)
            {
                Catalog c = iterator.next();
                String nodeId = c.getNodeId();
                if (n.getId().equals(nodeId))
                {
                    children.add(c);
                    iterator.remove();
                }
            }
            n.setCatalogList(children);
        }
    }

    private List<Catalog> discoverCatalogs(RackHdCriteria rackHDCriteria) throws RackHdException
    {
        return discoverResourceAsList(rackHDCriteria, Endpoints.CATALOGS_V2, new ParameterizedTypeReference<List<Catalog>>()
        {
        });
    }

    private List<Node> discoverNodes(RackHdCriteria rackHDCriteria) throws RackHdException
    {
        return discoverResourceAsList(rackHDCriteria, Endpoints.NODE_V2, new ParameterizedTypeReference<List<Node>>()
        {
        });
    }

    /**
     * Does the discovery of a particular REST API Resource from RackHD and returns it in the form of a List of the Entity Type
     * corresponding to the class passed as parameter
     *
     * @param rackHdCriteria
     *            {@link RackHdCriteria} containing RackHD ip address and credentials
     * @param resourceEndpoints
     *            {@link Endpoints}Endpoint Resource used in the URI
     * @param paramTypeReference
     *            {@link ParameterizedTypeReference}contains the List return type
     * @param <Q>
     *            {@link Q} Resource
     * @return List of class objects recovered from the API
     * @throws RackHdException
     *             if discovery fails due to wrong criteria or bad parsing of the object
     */
    private <Q> List<Q> discoverResourceAsList(RackHdCriteria rackHdCriteria, Endpoints resourceEndpoints,
            ParameterizedTypeReference<List<Q>> paramTypeReference) throws RackHdException
    {
        ResponseEntity<List<Q>> responseEntityHD;

        try
        {
            responseEntityHD = restTemplate.exchange(ApplicationUtil.formatEndpointUrl(rackHdCriteria, resourceEndpoints), HttpMethod.GET,
                    null, paramTypeReference);
        }
        catch (Exception je)
        {
            try
            {
                loginAndSetDefaultHeaders(rackHdCriteria);
                responseEntityHD = restTemplate.exchange(ApplicationUtil.formatEndpointUrl(rackHdCriteria, resourceEndpoints),
                        HttpMethod.GET, null, paramTypeReference);
            }
            catch (Exception e)
            {
                throw new RackHdException("Error discovering resource: " + paramTypeReference.getType(), e);
            }
        }

        return responseEntityHD.getBody();
    }

    /**
     * Login process which creates a session inside the RackHD API by using the credentials and ip address passed as parameters and recovers
     * an authorization token which is added as a default header in this services Rest Template.
     *
     * @param rackHDCriteria
     *            object containing RackHD ip address and credentials
     * @throws RackHdException
     */
    private void loginAndSetDefaultHeaders(RackHdCriteria rackHDCriteria) throws RackHdException
    {
        HttpHeaders headers = rackHdAuthenticationService.authenticateAndCreateHeaders(rackHDCriteria);
        restTemplate.setDefaultHeaders(headers);
    }
}
