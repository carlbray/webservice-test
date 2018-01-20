package com.carlbray.utils;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

/**
 * The Class PojoMapper.
 * 
 * Generic method for mapping Json objects into POJOs
 *
 * @param <T> the generic type
 */
public class PojoMapper<T> {

	private static final ContentType JSON = ContentType.JSON;

	/**
	 * Map objects.
	 *
	 * @param path the URL path to be added to the base URL
	 * @param pojo the Plain Old Java Object (POJO)
	 * @return the mapped pojo instance
	 */
	public T mapJsonObjects(String path, Class<T> pojo) {
		
		RequestSpecification requestSpec = RestUtils.buildDefaultRequestSpecification();
		Cookies cookies = RestUtils.getCookies(requestSpec);
		
		return given()
				.spec(requestSpec)
				.cookies(cookies)
			.log()
				.all()
			.when()
				.get(path)
			.then()
				.contentType(JSON)
				.extract()
				.as(pojo);
	}
}
