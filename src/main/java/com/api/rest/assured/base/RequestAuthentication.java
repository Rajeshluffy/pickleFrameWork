package com.api.rest.assured.base;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import java.util.Map;
import java.time.Instant;

public class RequestAuthentication {

	private String token;
	private String setOAuthBaseUrl;
	private static Instant tokenExpiryTime; // Store token expiry time
	private static final int TOKEN_VALIDITY_SECONDS = 3600; // Adjust based on API response


	public RequestAuthentication setOAuthBaseUrl(String setOAuthBaseUrl) {
		this.setOAuthBaseUrl = setOAuthBaseUrl;
		return this;
	}

	/**
	 * 
	 * Retrieves OAuth2 Token
	 * @param grantType
	 * @param clientId
	 * @param clientSecret
	 * @param username
	 * @param password
	 * @return
	 */
	public String getOAuthToken(String grantType, String clientId, String clientSecret, String username, String password) {
		if (token == null) {  // Prevents fetching a new token if already available
			token = given()
					.contentType(ContentType.URLENC)
					.formParam("grant_type", grantType)
					.formParam("client_id", clientId)
					.formParam("client_secret", clientSecret)
					.formParam("username", username)
					.formParam("password", password)
					.when()
					.post(setOAuthBaseUrl)
					.then()
					.extract()
					.path("access_token");  // Extracts token from response
		}
		tokenExpiryTime = Instant.now().plusSeconds(TOKEN_VALIDITY_SECONDS);
		return token;
	}

	/**
	 * Retrieves Token with  Headers
	 * @param grantType
	 * @param clientId
	 * @param clientSecret
	 * @param username
	 * @param password
	 * @param headers
	 * @return
	 */

	public String getOAuthToken(String grantType, String clientId, String clientSecret, 
			String username, String password, Map<String, String> headers ) {
		if (token == null) {
			token = given()
					.contentType(ContentType.URLENC)
					.formParam("grant_type", grantType)
					.formParam("client_id", clientId)
					.formParam("client_secret", clientSecret)
					.formParam("username", username)
					.formParam("password", password)
					.when()
					.post(setOAuthBaseUrl)
					.then()
					.extract()
					.path("access_token");
		}
		return token;
	}

	public String refreshOAuthToken(String grantType, String clientId, String clientSecret,String refreshToken ) {
		token = given()
				.contentType(ContentType.URLENC)
				.formParam("grant_type", grantType)
				.formParam("client_id", clientId)
				.formParam("client_secret", clientSecret)
				.formParam("refresh_token",refreshToken)
				.when()
				.post(setOAuthBaseUrl)
				.then()
				.extract()
				.path("access_token");

		return token;
	}





	/**
	 * Resets the token (useful when token expires)
	 */
	public void resetToken() {
		token = null;
	}
}

