package com.cooksys.second.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials {

	@Column(nullable = false, unique = true)
	private String username;//specifications say this should be part of read-only form. I'm not sure why we would have a Dao for credentials
	@Column(nullable = false)
	private String password;
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
