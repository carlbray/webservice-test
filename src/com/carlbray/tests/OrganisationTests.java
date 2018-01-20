package com.carlbray.tests;

import java.io.IOException;
import java.util.Optional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.carlbray.pojos.Organisation;
import com.carlbray.pojos.Sector;
import com.carlbray.pojos.Service;
import com.carlbray.utils.CSVReader;
import com.carlbray.utils.PojoMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.RestAssured;

/*
 * Assumptions:
 * 1. Search is initiated on organisation.id
 * 2. Organisation is returned in first 20 results
 * 3. Service is up
 * 
 * Acceptance Criteria:
 * 1. Name check = Inland Revenue Department
 * 2. Sector Id check = 5
 * 3. Sector Name check = Public Service
 * 4. Description contains = “Treaty of Waitangi”
*/

public class OrganisationTests {

	private static final String DATA_PROVIDER_METHOD = "getData";
	private static final String TEST_DATA_CSV = "data\\org-test-data.csv";
	private static final String BASE_PATH = "/api/v2/organisation/";
	private static final String BASE_URI = "https://www.govt.nz";

	private Service service;

	@Test(dataProvider = DATA_PROVIDER_METHOD)
	public void checkOrganisationName(String id, String name, String description, String sectorId, String sectorName) {
		
		Assert.assertEquals(findOrganisation(id).getName(),name);		
	}
	
	@Test(dataProvider = DATA_PROVIDER_METHOD)
	public void checkOrganisationDescriptionContains(String id, String name, String description, String sectorId, String sectorName) {
		
		Assert.assertTrue(findOrganisation(id).getDescription().contains(description));
	}
	
	@Test(dataProvider = DATA_PROVIDER_METHOD)
	public void checkSectorId(String id, String name, String description, String sectorId, String sectorName) {
		
		Sector sector = findOrganisation(id).getSector();
		Assert.assertEquals(sector.getId(), Integer.valueOf(sectorId));
	}		
	
	@Test(dataProvider = DATA_PROVIDER_METHOD)
	public void checkSectorName(String id, String name, String description, String sectorId, String sectorName) {
	
		Sector sector = findOrganisation(id).getSector();
		Assert.assertEquals(sector.getName(), sectorName);
	}

	@DataProvider	
	public static String[][] getData() throws JsonParseException, JsonMappingException, IOException {
		
		return CSVReader.get(TEST_DATA_CSV);
	}
	
	@BeforeClass
	public void setUp() {

		RestAssured.baseURI = BASE_URI;
		RestAssured.basePath = BASE_PATH;
		
		PojoMapper<Service> pojoMapper = new PojoMapper<Service>();
		service = pojoMapper.mapObjects("list", Service.class);
	}

	private Organisation findOrganisation(String id) {
		
		Optional<Organisation> organisation = service.getOrganisations().stream()
			.filter(org -> org.getId() == 926)
			.findFirst();
		
		Assert.assertTrue(organisation.isPresent(), "Organisation not found: " + id);
		return organisation.get();
	}
}
