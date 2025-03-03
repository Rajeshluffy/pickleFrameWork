package com.serivcenow.api.services;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;

import com.api.rest.assured.base.ResponseContentType;
import com.servicenow.pojos.IncidentRequestPayload;

import io.restassured.http.ContentType;

public class IncidentSerivce extends ServiceNow {
	
	private static final String TABLE_NAME = "incident";	
	
	public IncidentSerivce() {
		requestBuilder = globalRequest();
	}
	
	public IncidentSerivce fetchIncidentRecords() {
		response = restAssured.get(requestBuilder.build(), TABLE_NAME);
		return this;
	}
	
	public IncidentSerivce fetchIncidentRecord(String sysId) {
		response = restAssured.get(requestBuilder.build(), TABLE_NAME+"/"+sysId);
		return this;
	}
	
	public IncidentSerivce fetchIncidentRecordByNumber(String number) {
		response = restAssured.get(requestBuilder
				                   .setQueryParamKey("sysparm_query")
				                   .setQueryParamValue("number="+number)
				                   .build(), 
				                   TABLE_NAME);
		return this;
	}
	
	public IncidentSerivce createIncidentRecord() {
		response = restAssured.post(requestBuilder.setContentType(ContentType.JSON).build(), TABLE_NAME);
		return this;
	}
	
	public IncidentSerivce createIncidentRecord(IncidentRequestPayload payload) {
		response = restAssured.post(requestBuilder.build(), TABLE_NAME, payload);
		return this;
	}
	
	public IncidentSerivce updateIncidentRecord(IncidentRequestPayload payload, String sysId) {
		response = restAssured.put(requestBuilder.setContentType(ContentType.JSON).build(),  TABLE_NAME+"/"+sysId, payload);
		return this;
	}
	
	public IncidentSerivce fetchOnlyHardwareCategoryIncidentRecords() {		
		response = restAssured.get(requestBuilder.build().queryParam("sysparm_query", "category=hardware"), TABLE_NAME);
		return this;
	}
	
	public IncidentSerivce deleteIncidentRecord(String sysId) {
		response = restAssured.delete(requestBuilder.build(), TABLE_NAME+"/"+sysId);
		return this;
	}
	
	public IncidentSerivce validateSuccessResponse() {		
		MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getStatusMessage(), Matchers.equalToIgnoringCase("OK"));
		MatcherAssert.assertThat(response.getContentType(), Matchers.equalTo(ResponseContentType.JSON));
		return this;
	}
	
	public IncidentSerivce validateCreationResponse() {
		MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(201));
		MatcherAssert.assertThat(response.getStatusMessage(), Matchers.equalToIgnoringCase("Created"));
		MatcherAssert.assertThat(response.getContentType(), Matchers.equalTo(ResponseContentType.JSON));
		return this;
	}
	
	public IncidentSerivce validateDeletionResponse() {
		MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(204));
		MatcherAssert.assertThat(response.getStatusMessage(), Matchers.equalToIgnoringCase(ResponseContentType.JSON));
		return this;
	}
	
	public IncidentSerivce validateNotFoundResponse() {
		MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(404));
		MatcherAssert.assertThat(response.getStatusMessage(), Matchers.equalToIgnoringCase("Not Found"));
		MatcherAssert.assertThat(response.getContentType(), Matchers.equalTo(ResponseContentType.JSON));
		return this;
	}
	
	public IncidentSerivce validateCategories(String expected) {
		// To Get JSONString
		String responseBody = response.getBody();
		// Convert JSONString to JSONObject
		JSONObject json = new JSONObject(responseBody);
		JSONArray jsonArray = json.getJSONArray("result");
		for (Object record : jsonArray) {
			JSONObject jsonObject = (JSONObject) record;
			MatcherAssert.assertThat(jsonObject.getString("category"), Matchers.equalToIgnoringCase(expected));
		}
		return this;
	}
	
	public IncidentSerivce validateSysId(String expected) {
		// JSONString
		String responseBody = response.getBody();
		//Convert JSONString into JSONObject
		JSONObject json = new JSONObject(responseBody);
		String actual = json.getJSONObject("result").getString("sys_id");
		MatcherAssert.assertThat(actual, Matchers.equalTo(expected));
		return this;
	}
	
	public IncidentSerivce validateIncidentNumber(String expected) {		
		String actual = new JSONObject(response.getBody())
				            .getJSONArray("result").getJSONObject(0)
				            .getString("number");
		MatcherAssert.assertThat(actual, Matchers.equalTo(expected));
		return this;
	}

}
