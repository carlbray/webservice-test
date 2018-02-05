package com.carlbray.test;

import java.util.Optional;
import java.util.function.Predicate;

import org.testng.Assert;

import com.carlbray.pojos.Organisation;
import com.carlbray.pojos.Service;
import com.carlbray.utils.RestUtils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class OrganisationFetcher {

	/** Query parameters to limit the results */
	private static final String QUERY_PARAM_LIMIT_KEY = "limit";

	/**
	 * Query parameters to limit value as define on
	 * https://www.govt.nz/about/about-this-website/api/
	 */
	private static final String QUERY_PARAM_LIMIT_VALUE = "all";

	/** Command path for service under test */
	private static final String PATH_UNDER_TEST = "list";

	/** Base path for service under test */
	private static final String BASE_PATH = "/api/v2/organisation/";

	/** Base URI for service under test */
	protected static final String BASE_URI = "https://www.govt.nz";

	/** Instance of the mapped response under test. */
	private final Service service;

	public OrganisationFetcher() {

		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.addRequestSpecification(RestUtils.buildDefaultRequestSpecification(BASE_URI, BASE_PATH))
				.addQueryParam(QUERY_PARAM_LIMIT_KEY, QUERY_PARAM_LIMIT_VALUE).build();

		RestAssured.requestSpecification = requestSpecification;
		RestAssured.responseSpecification = RestUtils.buildDefaultResponseSpecification();

		// Get the response and map it to the Service POJO so we can test against it.
		service = RestUtils.mapJsonObjects(PATH_UNDER_TEST, Service.class);
	}

	/**
	 * Helper to find the right organisation in the response. It will check the the
	 * organisation was found
	 *
	 * @param id
	 *            the organisation id
	 * @return the organisation
	 */
	public Organisation findOrganisation(String id) {

		Predicate<? super Organisation> predicate = org -> org.getId() == Integer.parseInt(id);
		Optional<Organisation> organisation = service.getOrganisations().stream().filter(predicate).findFirst();

		Assert.assertTrue(organisation.isPresent(), "Organisation not found: " + id);
		return organisation.get();
	}
}
