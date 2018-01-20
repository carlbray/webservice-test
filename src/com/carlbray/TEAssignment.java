package com.carlbray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "query", "organisations" })
public class TEAssignment {

	@JsonProperty("query")
	private Query query;
	@JsonProperty("organisations")
	private List<Organisation> organisations = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public TEAssignment() {
	}

	/**
	 * 
	 * @param query
	 * @param organisations
	 */
	public TEAssignment(Query query, List<Organisation> organisations) {
		super();
		this.query = query;
		this.organisations = organisations;
	}

	@JsonProperty("query")
	public Query getQuery() {
		return query;
	}

	@JsonProperty("query")
	public void setQuery(Query query) {
		this.query = query;
	}

	@JsonProperty("organisations")
	public List<Organisation> getOrganisations() {
		return organisations;
	}

	@JsonProperty("organisations")
	public void setOrganisations(List<Organisation> organisations) {
		this.organisations = organisations;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("query", query).append("organisations", organisations)
				.append("additionalProperties", additionalProperties).toString();
	}

}