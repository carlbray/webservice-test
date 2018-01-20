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
 * 2. Organisation is returned in first 20 results!
 * 3. Service is up
 * 
 * Acceptance Criteria:
 * 1. Name check = Inland Revenue Department
 * 2. Sector Id check = 5
 * 3. Sector Name check = Public Service
 * 4. Description contains = “Treaty of Waitangi”
 * 
*/

public class OrganisationTests {

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

		RestAssured.baseURI = BASE_URI;
		RestAssured.basePath = BASE_PATH;
		
		// Get the response and map it to Service so we can test against it.
		PojoMapper<Service> pojoMapper = new PojoMapper<Service>();
		service = pojoMapper.mapJsonObjects("list", Service.class);
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
