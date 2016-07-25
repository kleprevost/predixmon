/******************************************************************************
 * Copyright (c) 2015 GE Digital. All rights reserved.                *
 *                                                                            *
 * The computer software herein is the property of GE Global Research. The    *
 * software may be used and/or copied only with the written permission of     *
 * GE Global Research or in accordance with the terms and conditions          *
 * stipulated in the agreement/contract under which the software has been     *
 * supplied.                                                                  *
 ******************************************************************************/

package com.ge.apm.sample.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ge.apm.sample.util.RestClient;

@SuppressWarnings("WeakerAccess")
@RestController
@RequestMapping("/v1")
public class AssetService {
    private static final Logger log = LoggerFactory.getLogger(AssetService.class);
    @Autowired
    private RestClient restClient;

    @RequestMapping(value= "/assets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> getAssets(HttpServletRequest request) {

        log.info("Fetching Assets");
        String url = this.restClient.getAssetUrl() + "/assets?components=BASIC&pageSize=1000";
        ResponseEntity<String> response = this.restClient.execute(request, url, HttpMethod.GET, "");
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    
}
