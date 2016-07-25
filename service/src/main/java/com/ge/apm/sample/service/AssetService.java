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

import com.ge.apm.sample.util.TimeseriesHeadersRequest;
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

    @RequestMapping(value= "/long", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> timeseries(HttpServletRequest request) {
        log.info("Fetching TS");
        TimeseriesHeadersRequest request2 = new TimeseriesHeadersRequest(request);
        request2.addHeader("Authorization", "bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxYjVmYjY1MC0wZjI3LTRmMzktYTI5OC1iOTlhZTZmZjk1MjUiLCJzdWIiOiIwYWVjYmRiZi1jOGMzLTRiNjItYmM5Yy01ZjE2ZDhlYzNkZTciLCJzY29wZSI6WyJwYXNzd29yZC53cml0ZSIsIm9wZW5pZCJdLCJjbGllbnRfaWQiOiJpbmdlc3Rvci45Y2YzM2NlMzdiZjY0YzU2ODFiNTE1YTZmNmFhZGY0NyIsImNpZCI6ImluZ2VzdG9yLjljZjMzY2UzN2JmNjRjNTY4MWI1MTVhNmY2YWFkZjQ3IiwiYXpwIjoiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJncmFudF90eXBlIjoicGFzc3dvcmQiLCJ1c2VyX2lkIjoiMGFlY2JkYmYtYzhjMy00YjYyLWJjOWMtNWYxNmQ4ZWMzZGU3Iiwib3JpZ2luIjoidWFhIiwidXNlcl9uYW1lIjoicG9rZW1vbiIsImVtYWlsIjoiZGF2aWQuc3RlaW5iZXJnZXJAZ2UuY29tIiwiYXV0aF90aW1lIjoxNDY5NDIzNTIzLCJyZXZfc2lnIjoiYmRiZTQ5YWYiLCJpYXQiOjE0Njk0MjM1MjMsImV4cCI6MTQ2OTUwOTkyMywiaXNzIjoiaHR0cHM6Ly9kOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQucHJlZGl4LXVhYS5ydW4uYXdzLXVzdzAyLXByLmljZS5wcmVkaXguaW8vb2F1dGgvdG9rZW4iLCJ6aWQiOiJkOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQiLCJhdWQiOlsiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJwYXNzd29yZCIsIm9wZW5pZCJdfQ.lFD6bHFJXuZH2Awxatp1MvHVDUHNTUpTckrXNASWeYfz6GV5DDWe9WIGYzTyTrN5UiaFi95MwzVrNR9OGh59VAQ-vvBpo5G7WlG8HILJHhBPgMSXSYtNf4qgMzwHPay5RqVmurZVZ8D4xiy9ZBWOSCZqnOtIEt25VA_hMhI8wPvXGHkU79YqbpLpyco6WACQ-IxrfVRB1EJvZnoZz4JIG84iDrfbt4pVPhbSYvrJ_kzyRX71NcYhNcED4dNYEftCQxxUBHkOcuXxcpAvJWJSEeaQAjELQ6LU7hMO5HmPc0Ztip2yl-2n0etL-0V73ly0udAZdmqb4Wm65QvsBHlDmg");

        String url = "https://apm-timeseries-services-hackapm.run.aws-usw02-pr.ice.predix.io/v2/time_series?operation=raw&tagList=POKE5-CASINO-COSMOPOLITAN-PM_UNIT-LONGITUDE&startTime=2015-12-31T00:28:03.000Z";
        ResponseEntity<String> response = this.restClient.execute(request2, url, HttpMethod.GET, "");
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    @RequestMapping(value= "/lat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> lat(HttpServletRequest request) {
        log.info("Fetching TS");
        TimeseriesHeadersRequest request2 = new TimeseriesHeadersRequest(request);
        request2.addHeader("Authorization", "bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxYjVmYjY1MC0wZjI3LTRmMzktYTI5OC1iOTlhZTZmZjk1MjUiLCJzdWIiOiIwYWVjYmRiZi1jOGMzLTRiNjItYmM5Yy01ZjE2ZDhlYzNkZTciLCJzY29wZSI6WyJwYXNzd29yZC53cml0ZSIsIm9wZW5pZCJdLCJjbGllbnRfaWQiOiJpbmdlc3Rvci45Y2YzM2NlMzdiZjY0YzU2ODFiNTE1YTZmNmFhZGY0NyIsImNpZCI6ImluZ2VzdG9yLjljZjMzY2UzN2JmNjRjNTY4MWI1MTVhNmY2YWFkZjQ3IiwiYXpwIjoiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJncmFudF90eXBlIjoicGFzc3dvcmQiLCJ1c2VyX2lkIjoiMGFlY2JkYmYtYzhjMy00YjYyLWJjOWMtNWYxNmQ4ZWMzZGU3Iiwib3JpZ2luIjoidWFhIiwidXNlcl9uYW1lIjoicG9rZW1vbiIsImVtYWlsIjoiZGF2aWQuc3RlaW5iZXJnZXJAZ2UuY29tIiwiYXV0aF90aW1lIjoxNDY5NDIzNTIzLCJyZXZfc2lnIjoiYmRiZTQ5YWYiLCJpYXQiOjE0Njk0MjM1MjMsImV4cCI6MTQ2OTUwOTkyMywiaXNzIjoiaHR0cHM6Ly9kOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQucHJlZGl4LXVhYS5ydW4uYXdzLXVzdzAyLXByLmljZS5wcmVkaXguaW8vb2F1dGgvdG9rZW4iLCJ6aWQiOiJkOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQiLCJhdWQiOlsiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJwYXNzd29yZCIsIm9wZW5pZCJdfQ.lFD6bHFJXuZH2Awxatp1MvHVDUHNTUpTckrXNASWeYfz6GV5DDWe9WIGYzTyTrN5UiaFi95MwzVrNR9OGh59VAQ-vvBpo5G7WlG8HILJHhBPgMSXSYtNf4qgMzwHPay5RqVmurZVZ8D4xiy9ZBWOSCZqnOtIEt25VA_hMhI8wPvXGHkU79YqbpLpyco6WACQ-IxrfVRB1EJvZnoZz4JIG84iDrfbt4pVPhbSYvrJ_kzyRX71NcYhNcED4dNYEftCQxxUBHkOcuXxcpAvJWJSEeaQAjELQ6LU7hMO5HmPc0Ztip2yl-2n0etL-0V73ly0udAZdmqb4Wm65QvsBHlDmg");

        String url = "https://apm-timeseries-services-hackapm.run.aws-usw02-pr.ice.predix.io/v2/time_series?operation=raw&tagList=POKE5-CASINO-COSMOPOLITAN-PM_UNIT-LATITUDE&startTime=2015-12-31T00:28:03.000Z";
        ResponseEntity<String> response = this.restClient.execute(request2, url, HttpMethod.GET, "");
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
    @RequestMapping(value= "/id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> id(HttpServletRequest request) {
        log.info("Fetching TS");
        TimeseriesHeadersRequest request2 = new TimeseriesHeadersRequest(request);
        request2.addHeader("Authorization", "bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxYjVmYjY1MC0wZjI3LTRmMzktYTI5OC1iOTlhZTZmZjk1MjUiLCJzdWIiOiIwYWVjYmRiZi1jOGMzLTRiNjItYmM5Yy01ZjE2ZDhlYzNkZTciLCJzY29wZSI6WyJwYXNzd29yZC53cml0ZSIsIm9wZW5pZCJdLCJjbGllbnRfaWQiOiJpbmdlc3Rvci45Y2YzM2NlMzdiZjY0YzU2ODFiNTE1YTZmNmFhZGY0NyIsImNpZCI6ImluZ2VzdG9yLjljZjMzY2UzN2JmNjRjNTY4MWI1MTVhNmY2YWFkZjQ3IiwiYXpwIjoiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJncmFudF90eXBlIjoicGFzc3dvcmQiLCJ1c2VyX2lkIjoiMGFlY2JkYmYtYzhjMy00YjYyLWJjOWMtNWYxNmQ4ZWMzZGU3Iiwib3JpZ2luIjoidWFhIiwidXNlcl9uYW1lIjoicG9rZW1vbiIsImVtYWlsIjoiZGF2aWQuc3RlaW5iZXJnZXJAZ2UuY29tIiwiYXV0aF90aW1lIjoxNDY5NDIzNTIzLCJyZXZfc2lnIjoiYmRiZTQ5YWYiLCJpYXQiOjE0Njk0MjM1MjMsImV4cCI6MTQ2OTUwOTkyMywiaXNzIjoiaHR0cHM6Ly9kOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQucHJlZGl4LXVhYS5ydW4uYXdzLXVzdzAyLXByLmljZS5wcmVkaXguaW8vb2F1dGgvdG9rZW4iLCJ6aWQiOiJkOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQiLCJhdWQiOlsiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJwYXNzd29yZCIsIm9wZW5pZCJdfQ.lFD6bHFJXuZH2Awxatp1MvHVDUHNTUpTckrXNASWeYfz6GV5DDWe9WIGYzTyTrN5UiaFi95MwzVrNR9OGh59VAQ-vvBpo5G7WlG8HILJHhBPgMSXSYtNf4qgMzwHPay5RqVmurZVZ8D4xiy9ZBWOSCZqnOtIEt25VA_hMhI8wPvXGHkU79YqbpLpyco6WACQ-IxrfVRB1EJvZnoZz4JIG84iDrfbt4pVPhbSYvrJ_kzyRX71NcYhNcED4dNYEftCQxxUBHkOcuXxcpAvJWJSEeaQAjELQ6LU7hMO5HmPc0Ztip2yl-2n0etL-0V73ly0udAZdmqb4Wm65QvsBHlDmg");

        String url = "https://apm-timeseries-services-hackapm.run.aws-usw02-pr.ice.predix.io/v2/time_series?operation=raw&tagList=POKE5-CASINO-COSMOPOLITAN-PM_UNIT-ID&startTime=2015-12-31T00:28:03.000Z";
        ResponseEntity<String> response = this.restClient.execute(request2, url, HttpMethod.GET, "");
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
}
