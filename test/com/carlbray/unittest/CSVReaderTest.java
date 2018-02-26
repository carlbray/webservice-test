package com.carlbray.unittest;

import java.io.IOException;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.carlbray.utils.CSVReader;
import com.fasterxml.jackson.core.JsonProcessingException;

class CSVReaderTest {

	@Test
	void testGet() throws JsonProcessingException, IOException {
		
		String[][] orgs = CSVReader.get("data\\org-test-data.csv");
		for (String[] org : orgs) {
			System.out.println(Arrays.toString(org));
			Assert.assertEquals(org[0], "926");
			Assert.assertEquals(org[1], "Inland Revenue Department");
			Assert.assertEquals(org[2], "Treaty of Waitangi");
			Assert.assertEquals(org[3], "5");
			Assert.assertEquals(org[4], "Public Service");			
		}
	}

}
