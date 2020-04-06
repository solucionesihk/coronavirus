/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coronavirus.restclient;

import com.coronavirus.util.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author eduar
 */
@Service
public class RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    
    @Autowired
    private Util responseRestClientUtil;
    
    public ObjectNode getStatistics(String country) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String start = country.substring(0,1).toUpperCase();
        String end = country.substring(1).toLowerCase();
        StringBuilder countryModify = new StringBuilder();
        countryModify.append(start);
        countryModify.append(end);
        LOGGER.info( "start: {}", start);
        LOGGER.info( "end: {}", end);
        headers.add( "Accept", "application/json" );
        headers.add( "x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com" );
        headers.add( "x-rapidapi-key", "ee704c2b59msh516429a6ba62ff6p1ab71cjsnc1c01cd03d0a" );

        HttpEntity<String> entity = new HttpEntity<>(headers);
        StringBuilder url = new StringBuilder();
        url.append( "https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country=" );
        url.append( countryModify.toString() );
        LOGGER.info("Country: {}", countryModify.toString());
        
        return responseRestClientUtil.responseStringGet(url, entity);
        
    }
    
/*    public ObjectNode get (String country){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        ObjectNode statistics = getStatistics(country);
        ArrayNode array = (ArrayNode)statistics.get("response").get("data").get("covid19Stats");
            
        //country
        if(array.size()>1)
        {
            String stat = statistics.get("response").get("data").get("covid19Stats").get(0).get("country").asText();
            String lastUpdate = statistics.get("response").get("data").get("covid19Stats").get(0).get("lastUpdate").asText();

            List<JsonNode> listConfirmed = array.findValues("confirmed");
            Integer sumConfirmed = 0;
            sumConfirmed = listConfirmed.stream().map((l) -> l.asInt()).reduce(sumConfirmed, Integer::sum);
            
            List<JsonNode> listDeaths = array.findValues("deaths");
            Integer sumDeaths = 0;
            sumDeaths = listDeaths.stream().map((l) -> l.asInt()).reduce(sumDeaths, Integer::sum);
            
            List<JsonNode> listRecovered = array.findValues("recovered");
            Integer sumRecovered = 0;
            sumRecovered = listRecovered.stream().map((l) -> l.asInt()).reduce(sumRecovered, Integer::sum);
            
            array.removeAll();
            
            json.put("city", "");
            json.put("province", "");
            json.put("country", stat);
            json.put("lastUpdate", lastUpdate);
            json.put("keyId", stat);
            json.put("confirmed", sumConfirmed);
            json.put("deaths", sumDeaths);
            json.put("recovered", sumRecovered);
            
            array.add(json);
            return statistics;
        }

        return statistics;
    }
*/
}
