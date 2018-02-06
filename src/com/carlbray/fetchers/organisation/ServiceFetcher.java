package com.carlbray.fetchers.organisation;

import java.util.Collections;
import java.util.Map;

import com.carlbray.fetchers.Fetchable;
import com.carlbray.pojos.organisation.Service;
import com.carlbray.utils.RestUtils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ServiceFetcher implements Fetchable {

	/** Base URI for service under test */
	protected static final String BASE_URI = "https://www.govt.nz";
	
	/** Query parameters to limit the results */
	private static final String QUERY_PARAM_LIMIT_KEY = "limit";

	/**
	 * Query parameters to limit value as define on
	 * https://www.govt.nz/about/about-this-website/api/
	 */
	private static final String QUERY_PARAM_LIMIT_VALUE = "all";
	
	private static final Map<String, ?> PARAMETERS_MAP = Collections.singletonMap(QUERY_PARAM_LIMIT_KEY, QUERY_PARAM_LIMIT_VALUE);

	/** Command path for service under test */
	private static final String PATH_UNDER_TEST = "list";

	/** Base path for service under test */
	private static final String BASE_PATH = "/api/v2/organisation/";

	/** Instance of the mapped response under test. */
	private final Service service;	
	
	public ServiceFetcher() {

		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.addRequestSpecification(RestUtils.buildDefaultRequestSpecification(BASE_URI, BASE_PATH))
				.addQueryParams(PARAMETERS_MAP).build();

		RestAssured.requestSpecification = requestSpecification;
		RestAssured.responseSpecification = RestUtils.buildDefaultResponseSpecification();
		
		// Get the response and map it to the Service POJO so we can test against it.
		service = RestUtils.mapJsonObjects(PATH_UNDER_TEST, Service.class);		
	}

	@Override
	public Service getService() {
		return service;
	}
}
