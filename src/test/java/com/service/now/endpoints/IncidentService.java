package com.service.now.endpoints;

import java.io.File;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.api.design.ResponseAPI;
import com.api.rest.assured.base.RestAssuredBase;
import io.restassured.specification.RequestSpecification;

public class IncidentService {	
	
	private ResponseAPI response;
	RestAssuredBase base = new RestAssuredBase();	
	public void createIncident(RequestSpecification request, String requestPayload) {
		response = base.post(request,"/incident", requestPayload);
	}
	
	
	public void createIncident(RequestSpecification request, File requestPayload) {
		response = base.post(request, "/incident", requestPayload);
	}
	
	public void createIncident(RequestSpecification request, Object requestPayload) {
		response = base.post(request, "/incident", requestPayload);
	}
	
	public void getIncident(RequestSpecification request) {
		response = base.get(request, "/incident");
	}
	
	public void getIncident(RequestSpecification request, String sysID) {
		response = base.get(request, "/incident"+"/"+sysID);
	}
	
	public void validateResponse(int statusCode, String statusLine) {
		MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(statusCode));
		MatcherAssert.assertThat(response.getStatusMessage(), Matchers.equalTo(statusLine));
	}

}