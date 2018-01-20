package com.carlbray.utils;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

public class PojoMapper<T> {

	public T mapObjects(String path, Class<T> pojo) {
		
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
				.contentType(ContentType.JSON)
				.extract()
				.as(pojo);
	}		
	
}
