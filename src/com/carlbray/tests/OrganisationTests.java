package com.carlbray.tests;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.carlbray.pojos.Organisation;
import com.carlbray.pojos.Organisations;
import com.carlbray.pojos.Sector;
import com.carlbray.utils.CSVReader;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

/*
 * 
 * Acceptance Criteria:
 * Name check = Inland Revenue Department
 * Sector Id check = 5
 * Sector Name check = Public Service
 * Description contains = “Treaty of Waitangi”
*/

public class OrganisationTests {

	private Cookies cookies;
	private RequestSpecification requestSpec;
	private Optional<Organisation> ird;

	@Test(dataProvider = "getData")
	public void checkOrganisationName(String id, String name, String description, String sectorId, String sectorName) {
		ird.ifPresent(check -> Assert.assertEquals(check.getName(),name));		
	}
	
	@Test(dataProvider = "getData")
	public void checkOrganisationDescriptionContains(String id, String name, String description, String sectorId, String sectorName) {		
		ird.ifPresent(check -> Assert.assertTrue(check.getDescription().contains(description)));
	}
	
	@Test(dataProvider = "getData")
	public void checkSectorId(String id, String name, String description, String sectorId, String sectorName) {
		Sector sector = ird.get().getSector();
		Assert.assertEquals(sector.getId(), Integer.valueOf(sectorId));
	}		
	
	@Test(dataProvider = "getData")
	public void checkSectorName(String id, String name, String description, String sectorId, String sectorName) {
		Sector sector = ird.get().getSector();
		Assert.assertEquals(sector.getName(), sectorName);
	}

	@DataProvider	
	public static String[][] getData() throws JsonParseException, JsonMappingException, IOException {
		
		return CSVReader.get("data\\org-test-data.csv");
	}
	
	@BeforeClass
	public void setUp() {

		RestAssured.baseURI = "https://www.govt.nz";
		RestAssured.basePath = "/api/v2/organisation/";
		
		buildRequestSpecification();
		getCookies();
		
		Organisations organisations = given()
				.spec(requestSpec)
				.cookies(cookies)
			.log()
				.all()
			.when()
				.get("list")
			.then()
				.contentType(ContentType.JSON)
				.extract()
				.as(Organisations.class);
		
		ird = organisations.getOrganisations().stream()
			.filter(org -> org.getId() == 926)
			.findFirst();
	}

	private void buildRequestSpecification() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate");
		
		requestSpec = new RequestSpecBuilder()
				.addHeaders(headers)
				.build();
	}
	
	private void getCookies() {
		String body = String.format("//json");
		cookies = RestAssured.given()
				.spec(requestSpec)
				.auth()
					.none()
	            //.contentType(ContentType.JSON)
				.log()
					.all()					
	            .when()
		            .body(body)
		            .get("list")
	            .then()
		            .statusCode(200)
		            .extract()
		            .response()
		            .getDetailedCookies();
	}
}
