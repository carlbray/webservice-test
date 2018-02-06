package com.carlbray.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class JsonSchema2Pojo {

	private static final String SRC_FOLDER = "src";
	private static final String POJO_JAVA_PACKAGE = "com.carlbray.pojos.organisation";
	private static final String ROOT_CLASS_NAME = "Service";
	private static final String EXPECTED_RESPONSE_JSON = "file:data/expectedResponse.json";

	public static void main(String[] args) throws IOException {
		JCodeModel codeModel = new JCodeModel();

		URL source = new URL(EXPECTED_RESPONSE_JSON);

		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() { // set config option by overriding method
				return true;
			}

			@Override
			public boolean isUsePrimitives() {
				return true;
			}

			@Override
			public boolean isUseCommonsLang3() {
				return true;
			}

			@Override
			public boolean isIncludeGetters() {
				return true;
			}

			@Override
			public SourceType getSourceType() {
				return SourceType.JSON;
			}
		};

		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
		mapper.generate(codeModel, ROOT_CLASS_NAME, POJO_JAVA_PACKAGE, source);

		codeModel.build(new File(SRC_FOLDER));
	}
}
