package com.carlbray;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "description", "sector", "parent" })
public class Organisation {

	@JsonProperty("id")
	private Integer id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("sector")
	private Sector sector;
	@JsonProperty("parent")
	private Integer parent;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Organisation() {
	}

	/**
	 * 
	 * @param sector
	 * @param id
	 * @param description
	 * @param name
	 * @param parent
	 */
	public Organisation(Integer id, String name, String description, Sector sector, Integer parent) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.sector = sector;
		this.parent = parent;
	}

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("sector")
	public Sector getSector() {
		return sector;
	}

	@JsonProperty("sector")
	public void setSector(Sector sector) {
		this.sector = sector;
	}

	@JsonProperty("parent")
	public Integer getParent() {
		return parent;
	}

	@JsonProperty("parent")
	public void setParent(Integer parent) {
		this.parent = parent;
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
		return new ToStringBuilder(this).append("id", id).append("name", name).append("description", description)
				.append("sector", sector).append("parent", parent).append("additionalProperties", additionalProperties)
				.toString();
	}

}
