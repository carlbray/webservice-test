package com.carlbray.utils;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

public class RestUtils {
	
	/**
	 * Builds the default request specification.
	 *
	 * @return the request specification
	 */
	public static RequestSpecification buildDefaultRequestSpecification() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate");
		
		return buildRequestSpecification(headers);
	}

	/**
	 * Builds the request specification.
	 *
	 * @param headers the headers
	 * @return the request specification
	 */
	public static RequestSpecification buildRequestSpecification(Map<String, String> headers) {
		return new RequestSpecBuilder()
				.addHeaders(headers)
				.build();
	}	

	/**
	 * Gets the cookies.
	 *
	 * @param requestSpec the request spec
	 * @return the cookies
	 */
	public static Cookies getCookies(RequestSpecification requestSpec) {
		return RestAssured.given()
				.spec(requestSpec)
				.auth()
					.none()
				.log()
					.all()					
	            .when()
		            .get()
	            .then()
		            .statusCode(200)
		            .extract()
		            .response()
		            .getDetailedCookies();
	}	
}
