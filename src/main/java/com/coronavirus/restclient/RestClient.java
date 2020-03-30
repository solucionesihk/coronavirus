/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coronavirus.restclient;

import com.coronavirus.util.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 *
 * @author eduar
 */
@Service
public class RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    
    @Autowired
    private Util responseRestClientUtil;
    
    public ObjectNode getStatistics(String conutry) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        headers.add("Accept", "application/json");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, authorization");
        headers.add("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS");
        headers.add("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com");
        headers.add("x-rapidapi-key", "ee704c2b59msh516429a6ba62ff6p1ab71cjsnc1c01cd03d0a");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        StringBuilder url = new StringBuilder();
        url.append( "https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country=" );
        url.append( conutry );
        LOGGER.info("Country: {}", conutry);
        
        return responseRestClientUtil.responseStringGet(url, entity);
        
    }
}
