package com.carlbray.test.organisation;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.carlbray.fetchers.organisation.OrganisationFetcher;
import com.carlbray.pojos.organisation.Sector;
import com.carlbray.test.RestTest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/*
 * Assumptions:
 * 1. Search is initiated on organisation.id
 * 2. Organisation id only appears once in the response
 * 
 * Acceptance Criteria:
 * 1. Name check = Inland Revenue Department
 * 2. Sector Id check = 5
 * 3. Sector Name check = Public Service
 * 4. Description contains = “Treaty of Waitangi” 
*/
public class OrganisationTest extends RestTest {

	private OrganisationFetcher fetcher;

	@Test(dataProvider = DATA_PROVIDER_METHOD)
	public void checkOrganisationName(String id, String name, String description, String sectorId, String sectorName) {
		
		Assert.assertEquals(fetcher.findOrganisation(id).getName(),name);		
	}
	
	@Test(dataProvider = DATA_PROVIDER_METHOD)
	public void checkOrganisationDescriptionContains(String id, String name, String description, String sectorId, String sectorName) {
		
		Assert.assertTrue(fetcher.findOrganisation(id).getDescription().contains(description));
	}
	
	@Test(dataProvider = DATA_PROVIDER_METHOD)
	public void checkSectorId(String id, String name, String description, String sectorId, String sectorName) {
		
		Sector sector = fetcher.findOrganisation(id).getSector();
		Assert.assertEquals(sector.getId(), Integer.parseInt(sectorId));
	}		
	
	@Test(dataProvider = DATA_PROVIDER_METHOD)
	public void checkSectorName(String id, String name, String description, String sectorId, String sectorName) {
	
		Sector sector = fetcher.findOrganisation(id).getSector();
		Assert.assertEquals(sector.getName(), sectorName);
	}

	/**
	 * Gets the test data for the dataProvider.
	 *
	 * @return the data
	 */
	@DataProvider	
	public static String[][] getData() throws JsonParseException, JsonMappingException, IOException {
		
		return OrganisationDataProvider.getData();
	}
	
	@BeforeClass
	public void setUp() {
		
		fetcher = new OrganisationFetcher();
	}
}
