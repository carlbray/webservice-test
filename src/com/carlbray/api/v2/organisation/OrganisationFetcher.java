package com.carlbray.api.v2.organisation;

import java.util.Optional;
import java.util.function.Predicate;

import org.testng.Assert;

import com.carlbray.pojos.organisation.Organisation;

public class OrganisationFetcher extends ServiceFetcher {

	/**
	 * Helper to find the right organisation in the response. It will check the the
	 * organisation was found
	 *
	 * @param id
	 *            the organisation id
	 * @return the organisation
	 */
	public Organisation getOrganisation(String id) {

		Predicate<? super Organisation> predicate = org -> org.getId() == Integer.parseInt(id);
		Optional<Organisation> organisation = getService().getOrganisations().stream().filter(predicate).findFirst();

		Assert.assertTrue(organisation.isPresent(), "Organisation not found: " + id);
		return organisation.get();
	}
}
