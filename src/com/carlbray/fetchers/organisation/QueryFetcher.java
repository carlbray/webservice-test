package com.carlbray.fetchers.organisation;

public class QueryFetcher extends ServiceFetcher {

	public int getCount() {
		return getService().getQuery().getCount();
	}
	
	public int getOffset() {
		return getService().getQuery().getOffset();
	}
}
