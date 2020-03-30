/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coronavirus.util;

import com.coronavirus.config.ConfigRestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author eduar
 */
@Service

public class Util {

    @Autowired
    private ConfigRestClient configRC;
    
    public ObjectNode responseStringGet(StringBuilder url, HttpEntity<String> entity){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        try {
                ResponseEntity<JsonNode> responseRest = configRC.getRestTemplate().exchange(url.toString(), HttpMethod.GET, entity, JsonNode.class);
                json.put("status", "OK");
                json.set("response", responseRest.getBody());
//                LOGGER.debug("Response: {}", responseRest.getBody());
        } catch (RestClientException e) {
                json.put("status", "ERROR");
                json.put("response", e.getMessage());
//                LOGGER.error("Error: {}", e.getMessage());
        }
        return json;
    }
}
