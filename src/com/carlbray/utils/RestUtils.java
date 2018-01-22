package com.carlbray.utils;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestUtils {
	
	/**
	 * Builds the default request specification.
	 *
	 * @return the request specification
	 */
	public static RequestSpecification buildDefaultRequestSpecification() {
		
		return new RequestSpecBuilder()
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
			.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
			.addHeader("Accept-Encoding", "gzip, deflate")
			.build();
	}

	/**
	 * Gets the cookies.
	 *
	 * @return the cookies
	 */
	public static Cookies getCookies() {
		return RestAssured.given()
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
	
	/**
	 * Map objects.
	 * @param <T>
	 *
	 * @param path the URL path to be added to the base URL
	 * @param pojo the Plain Old Java Object (POJO)
	 * @return the mapped pojo instance
	 */
	public static <T> T mapJsonObjects(String path, Class<T> pojo) {
		
		Cookies cookies = RestUtils.getCookies();
		
		return given()
				.cookies(cookies)
			.log()
				.all()
			.when()
				.get(path)
			.then()
				.log()
					.all()
				.contentType(ContentType.JSON)
				.extract()
				.as(pojo);
	}

	/**
	 * Builds the default response specification.
	 *
	 * @return the response specification
	 */	
	public static ResponseSpecification buildDefaultResponseSpecification() {

		return new ResponseSpecBuilder()
				.expectStatusCode(200)
				.build();
	}	
}
