package com.carlbray.test;

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
import com.carlbray.utils.RestUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.RestAssured;

/*
 * Assumptions:
 * 1. Search is initiated on organisation.id
 * 2. Organisation is returned in first 20 results!
 * 3. Service is up
 * 4. Organisation id only appears once in the response
 * 
 * Acceptance Criteria:
 * 1. Name check = Inland Revenue Department
 * 2. Sector Id check = 5
 * 3. Sector Name check = Public Service
 * 4. Description contains = “Treaty of Waitangi” 
*/
public class OrganisationTest {

	/** Command path for service under test */
	private static final String PATH_UNDER_TEST = "list";

	/** Method to be used by TestNG as the dataProvider */
	private static final String DATA_PROVIDER_METHOD = "getData";
	
	/** File to be used to get the testing data from */
	private static final String TEST_DATA_CSV = "data\\org-test-data.csv";
	
	/** Base path for service under test */
	private static final String BASE_PATH = "/api/v2/organisation/";
	
	/** Base URI for service under test */
	private static final String BASE_URI = "https://www.govt.nz";

	/** Instance of the mapped response under test. */
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

	/**
	 * Gets the CSV data for the dataProvider.
	 *
	 * @return the data
	 */
	@DataProvider	
	public static String[][] getData() throws JsonParseException, JsonMappingException, IOException {
		
		return CSVReader.get(TEST_DATA_CSV);
	}
	
	@BeforeClass
	public void setUp() {

		RestAssured.requestSpecification = RestUtils.buildDefaultRequestSpecification(BASE_URI, BASE_PATH);
		RestAssured.responseSpecification = RestUtils.buildDefaultResponseSpecification();
		
		// Get the response and map it to the Service POJO so we can test against it.
		service = RestUtils.mapJsonObjects(PATH_UNDER_TEST, Service.class);
	}

	/**
	 * Helper to find the right organisation in the response.
	 * It will check the the organisation was found
	 *
	 * @param id the organisation id
	 * @return the organisation
	 */
	private Organisation findOrganisation(String id) {
		
		Optional<Organisation> organisation = service.getOrganisations().stream()
			.filter(org -> org.getId().intValue() == Integer.parseInt(id))
			.findFirst();
		
		Assert.assertTrue(organisation.isPresent(), "Organisation not found: " + id);
		return organisation.get();
	}
}
