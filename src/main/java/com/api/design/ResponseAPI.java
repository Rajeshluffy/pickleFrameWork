package com.api.design;

import java.util.Map;

public interface ResponseAPI {
	
	int getStatusCode();
	String getStatusMessage();
	String getBody();
	Map<String, String> getHeaders();
	String getContentType();

}