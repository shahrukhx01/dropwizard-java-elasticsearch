package com.wesa.elasticsearch.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@JsonProperty
	private int id;

	@JsonProperty
	private String firstname;

	@JsonProperty
	private String lastname;

	@JsonProperty
	private String company;

	@JsonProperty
	private String city;

	@JsonProperty
	private String password;

	@JsonProperty
	private int role;

	@JsonProperty
	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isSuperAdmin() {
		if (this.getRole() == 100) {
			return true;
		} else {
			return false;
		}

	}
}
