package com.carlbray.api.v2.organisation;

public class QueryFetcher extends ServiceFetcher implements TestableQuery {

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	@Override
	public int getCount() {
		return getService().getQuery().getCount();
	}
	
	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	@Override
	public int getOffset() {
		return getService().getQuery().getOffset();
	}
}
