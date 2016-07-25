/******************************************************************************
 * Copyright (c) 2015 GE Digital. All rights reserved.                *
 *                                                                            *
 * The computer software herein is the property of GE Global Research. The    *
 * software may be used and/or copied only with the written permission of     *
 * GE Global Research or in accordance with the terms and conditions          *
 * stipulated in the agreement/contract under which the software has been     *
 * supplied.                                                                  *
 ******************************************************************************/

package com.ge.apm.sample.util;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.Enumeration;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Component
@Getter
public class RestClient {
    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    private RestTemplate restTemplate;
    @Value("${use.proxy:}")
    private String useProxy;
    @Value("${proxy.host:}")
    private String propertyProxyHost;
    @Value("${proxy.port:-1}")
    private int propertyProxyPort;

    @Value("${apm.cases.url}")
    private String casesUrl;
    @Value("${apm.blob.storage.url}")
    private String blobStorageUrl;
    @Value("${apm.asset.url}")
    private String assetUrl;
    @Value("${apm.config.url}")
    private String configUrl;
    @Value("${apm.alarm.url}")
    private String alarmUrl;
    @Value("${apm.note.url}")
    private String noteUrl;
    @Value("${apm.filter.url}")
    private String filterUrl;
    @Value("${apm.profile.url}")
    private String profileUrl;
    @Value("${apm.gateway.url}")
    private String gatewayUrl;
    @Value("${apm.timeseries.url}")
    private String timeseriesUrl;
    @Value("${apm.s95-adapter.url}")
    private String s95AdaterUrl;
    @Value("${apm.adapter.config.url}")
    private String adapterConfigUrl;

    @PostConstruct
    public void initialize() {
        this.restTemplate = new RestTemplate();

        Proxy proxy = getProxy();

        if (proxy != null) {
            logger.info("Used Proxy");
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setProxy(proxy);
            this.restTemplate.setRequestFactory(factory);
        }

        logger.info("casesUrl: '{}'", this.casesUrl);

        logger.info("blobStorageUrl: '{}'", this.blobStorageUrl);
        logger.info("assetUrl: '{}'", this.assetUrl);
        logger.info("configUrl: '{}'", this.configUrl);
        logger.info("alarmUrl: '{}'", this.alarmUrl);
        logger.info("noteUrl: '{}'", this.noteUrl);
        logger.info("filterUrl: '{}'", this.filterUrl);
        logger.info("profileUrl: '{}'", this.profileUrl);
        logger.info("gatewayUrl: '{}'", this.gatewayUrl);
        logger.info("timeseriesUrl: '{}'", this.timeseriesUrl);
        logger.info("s95AdaterUrl: '{}'", this.s95AdaterUrl);
        logger.info("adapterConfigUrl: '{}'", this.adapterConfigUrl);
    }

    private Proxy getProxy() {
        String envProxyHost = System.getProperty("https.proxyHost");

        if (StringUtils.hasText(envProxyHost)) {
            logger.info("************** Using command line parameters for proxy");
            int envProxyPort = Integer.valueOf(System.getProperty("https.proxyPort"));

            InetSocketAddress address = new InetSocketAddress(envProxyHost, envProxyPort);
            return new Proxy(Proxy.Type.HTTP, address);
        } else if (StringUtils.hasText(this.useProxy) && this.useProxy.equalsIgnoreCase("true")) {
            logger.info("************** Using application properties for proxy");
            if (!StringUtils.hasText(this.propertyProxyHost)) {
                throw new RuntimeException("The proxy host value is not provided.");
            }

            if (this.propertyProxyPort == -1) {
                throw new RuntimeException("The proxy port value is not provided.");
            }

            // for oauth2RestTemplate, the system property should be set.
            System.setProperty("https.proxyHost", this.propertyProxyHost);
            System.setProperty("https.proxyPort", String.valueOf(this.propertyProxyPort));

            InetSocketAddress address = new InetSocketAddress(this.propertyProxyHost, this.propertyProxyPort);
            return new Proxy(Proxy.Type.HTTP, address);
        }

        logger.info("************** No Proxy used");

        return null;
    }

    public ResponseEntity<String> execute(HttpServletRequest request, String url, HttpMethod httpMethod,
        String postBody) {
        HttpHeaders headers = getHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(postBody, headers);
        logger.info("Invoking url : '{}'", url);
        return this.restTemplate.exchange(url, httpMethod, entity, String.class);
    }

    private HttpHeaders getHeaders(HttpServletRequest request) {

        String authorization = "";
        String tenantUuid = "";
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            if (key.equalsIgnoreCase("Authorization")) {
                authorization = value;
            } else if (key.equalsIgnoreCase("tenant")) {
                tenantUuid = value;
            }
        }

        if (!StringUtils.hasText(authorization)) {
            throw new RuntimeException("Please provide value for the header 'Authorization'");
        }

        if (!StringUtils.hasText(tenantUuid)) {
            throw new RuntimeException("Please provide value for the header 'tenant'");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", Collections.singletonList("application/json"));
        headers.put("Authorization", Collections.singletonList(authorization));
        headers.put("tenant", Collections.singletonList(tenantUuid));
        return headers;
    }
}
