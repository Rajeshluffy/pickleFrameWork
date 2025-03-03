package com.api.rest.assured.base;

import com.api.design.ApiClient;
import com.api.design.ResponseAPI;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredBase implements ApiClient {
	
	private Response response;
	
	private RequestSpecification given(RequestSpecification requestSpecification) {
		return RestAssured.given()
				          .spec(requestSpecification)
				          .filter(new RestAssuredListener());
	}

	@Override
	public ResponseAPI get(RequestSpecification request, String endPoint) {
		response = given(request).get(endPoint);
		return new RestAssuredResponseBase(response);
	}

	@Override
	public ResponseAPI post(RequestSpecification request, String endPoint) {
		response = given(request)				   				   
				   .post(endPoint);
		return new RestAssuredResponseBase(response);
	}

	@Override
	public ResponseAPI post(RequestSpecification request, String endPoint, Object body) {
		response = given(request)				   
				   .body(body)
				   .post(endPoint);
		return new RestAssuredResponseBase(response);
	}	

	@Override
	public ResponseAPI put(RequestSpecification request, String endPoint, Object body) {
		return new RestAssuredResponseBase(
				   given(request)
				   .body(new Gson().toJson(body))
				   .put(endPoint));
	}

	@Override
	public ResponseAPI delete(RequestSpecification request, String endPoint) {
		return new RestAssuredResponseBase(given(request).delete(endPoint));
	}

}