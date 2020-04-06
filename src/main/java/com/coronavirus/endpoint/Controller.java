/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coronavirus.endpoint;

import com.coronavirus.config.ResourceNotFoundException;
import com.coronavirus.restclient.RestClient;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author eduar
 */
@RestController
@RequestMapping("api/v1")
public class Controller {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    
    @Autowired
    RestClient restClient;
    
    @GetMapping(value = "statistics-covid-19/stats")
    public ObjectNode getStatisticsByCountry( ) throws ResourceNotFoundException{
        try{
            ObjectNode statistics = restClient.getStatistics( "A" );
            return statistics;
        }
        catch(Exception e){
            LOGGER.error("Error, detail: {}", e);
            return null;
        }

    }
}
