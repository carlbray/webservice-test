package com.carlbray.api.v2.organisation;

import com.carlbray.pojos.organisation.Organisation;

public interface TestableOrganisation {

	/**
	 * Helper to find the right organisation in the response. It will check the the
	 * organisation was found
	 *
	 * @param id
	 *            the organisation id
	 * @return the organisation
	 */
	Organisation getOrganisation(String id);

}