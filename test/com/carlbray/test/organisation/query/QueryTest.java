package com.carlbray.test.organisation.query;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.carlbray.fetchers.organisation.QueryFetcher;
import com.carlbray.test.RestTest;

public class QueryTest extends RestTest {
	
	private QueryFetcher fetcher;
	
	@Test
	public void checkCountTest() {
		Assert.assertEquals(fetcher.getCount(), 381);
	}

	@BeforeClass
	public void setUp() {
		fetcher = new QueryFetcher();
	}
}
