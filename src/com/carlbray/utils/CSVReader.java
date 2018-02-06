package com.carlbray.utils;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

public class CSVReader {

	/**
	 * Gets the String arrays from a CSV file.
	 *
	 * @param filename the filename
	 * @return the string[][]
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String[][] get(String filename) throws JsonParseException, JsonMappingException, IOException {

		CsvMapper mapper = new CsvMapper().enable(CsvParser.Feature.WRAP_AS_ARRAY);
		return mapper.readValue(new File(filename), String[][].class);		
	}
}
