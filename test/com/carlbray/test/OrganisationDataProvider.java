package com.carlbray.test;

import java.io.IOException;

import com.carlbray.utils.CSVReader;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class OrganisationDataProvider {
	/** File to be used to get the testing data from */
	static final String TEST_DATA_CSV = "data\\org-test-data.csv";
	
	public static String[][] getData() throws JsonParseException, JsonMappingException, IOException {
		
		return CSVReader.get(TEST_DATA_CSV);
	}	
}
