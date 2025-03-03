package com.api.rest.assured.base;

import java.util.HashMap;
import java.util.Map;

import com.api.design.ResponseAPI;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class RestAssuredResponseBase implements ResponseAPI {
	
	private Response response;
	
	public RestAssuredResponseBase(Response response) {
		this.response = response;
	}

	@Override
	public int getStatusCode() {		
		return response.getStatusCode();
	}

	@Override
	public String getStatusMessage() {
		String[] statusLines = response.getStatusLine().split(" ", 3);
		return statusLines[statusLines.length - 1];
	}

	@Override
	public String getBody() {		
		return response.asString();
	}

	@Override
	public Map<String, String> getHeaders() {
		Map<String, String> map = new HashMap<String, String>();
		Headers headers = response.getHeaders();
		for (Header header : headers) {
			map.put(header.getName(), header.getValue());
		}
		return map;
	}

	@Override
	public String getContentType() {	
		String[] split = response.getContentType().split(";");
		return split[0];
	}

}