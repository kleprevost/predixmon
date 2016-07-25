package com.ge.apm.sample.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class TimeseriesHeadersRequest extends HttpServletRequestWrapper {
    private List<Header>_headers;

    public TimeseriesHeadersRequest(HttpServletRequest request) {
        super(request);

        this._headers = new LinkedList<Header>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            this._headers.add(new Header(key, value));
        }

    }

    public String getHeader(String name) {
        Header found = null;

        for (Header header : this._headers) {
            if (found != null && !found.name.isEmpty()) { continue; }

            if (header.name.toLowerCase().equals(name.toLowerCase())) {
                found = header;
            }
        }

        return (found != null) ? found.value : "";
    }

    public void addHeader(String name, String value) {

        for( Iterator<Header> it = this._headers.iterator(); it.hasNext() ; ) {
            Header header = it.next();
            if (header.name.toLowerCase().equals(name.toLowerCase())) {
                it.remove();
            }
        }

        this._headers.add(new Header(name, value));
    }

    public Enumeration getHeaderNames() {
        List list = new ArrayList();

        for (Header header : this._headers) {
            list.add(header.name);
        }

        Enumeration en = Collections.enumeration(list);
        return en;
    }
}

class Header {
    public final String name;
    public final String value;

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }
}