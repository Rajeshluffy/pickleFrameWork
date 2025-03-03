package com.api.rest.assured.base;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilder {

    private String baseUri;
    private String basePath;
    private ContentType contentType;
    private ContentType accept;
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private String queryParamKey;
    private String queryParamValue;
    private String pathParamKey;
    private String pathParamValue;
    private String headerKey;
    private String headerValue;
    private String username;
    private String password;
    private RequestSpecification specification;
    private boolean preemptive;

    public RequestSpecBuilder setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public RequestSpecBuilder setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public RequestSpecBuilder setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public RequestSpecBuilder setAccept(ContentType accept) {
        this.accept = accept;
        return this;
    }

    public RequestSpecBuilder setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public RequestSpecBuilder setQueryParamKey(String queryParamKey) {
        this.queryParamKey = queryParamKey;
        return this;
    }

    public RequestSpecBuilder setQueryParamValue(String queryParamValue) {
        this.queryParamValue = queryParamValue;
        return this;
    }

    public RequestSpecBuilder setPathParamValue(String pathParamValue) {
        this.pathParamValue = pathParamValue;
        return this;
    }

    public RequestSpecBuilder setPathParamKey(String pathParamKey) {
        this.pathParamKey = pathParamKey;
        return this;
    }

    public RequestSpecBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public RequestSpecBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public RequestSpecBuilder setSpecification(RequestSpecification specification) {
        this.specification = specification;
        return this;
    }

    public RequestSpecBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestSpecBuilder setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
        return this;
    }

    public RequestSpecBuilder setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
        return this;
    }

    public RequestSpecBuilder setPreemptive(boolean preemptive) {
        this.preemptive = preemptive;
        return this;
    }

    public RequestSpecBuilder setBasicAuth(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    public RequestSpecification build() {
        RequestSpecification requestSpecification = given();

        if (baseUri != null) {
            requestSpecification.baseUri(baseUri);
        }

        if (basePath != null) {
            requestSpecification.basePath(basePath);
        }

        if (contentType != null) {
            requestSpecification.contentType(contentType);
        }

        if (accept != null) {
            requestSpecification.accept(accept);
        }

        if (queryParamKey != null && queryParamValue != null) {
            requestSpecification.queryParam(queryParamKey, queryParamValue);
        }

        if (pathParamKey != null && pathParamValue != null) {
            requestSpecification.pathParam(pathParamKey, pathParamValue);
        }

        if (username != null && password != null) {
            if (preemptive) {
                requestSpecification.auth().preemptive().basic(username, password);
            } else {
                requestSpecification.auth().basic(username, password);
            }
        }

        if (!queryParams.isEmpty()) {
            requestSpecification.queryParams(queryParams);
        }

        if (specification != null) {
            requestSpecification.spec(specification);
        }

        if (headerKey != null && headerValue != null) {
            requestSpecification.header(headerKey, headerValue);
        }

        if (!headers.isEmpty()) {
            requestSpecification.headers(headers);
        }

        return requestSpecification;
    }
}
