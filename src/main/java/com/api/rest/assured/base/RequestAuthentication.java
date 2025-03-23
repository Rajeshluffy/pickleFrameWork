package com.api.rest.assured.base;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import java.util.Map;

public class RequestAuthentication {

    private String token;
    private String setOAuthBaseUrl;
    
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

    /**
     * Resets the token (useful when token expires)
     */
    public void resetToken() {
        token = null;
    }
}

