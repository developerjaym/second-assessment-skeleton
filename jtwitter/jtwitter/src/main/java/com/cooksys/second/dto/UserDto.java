package com.cooksys.second.dto;

import java.sql.Timestamp;

public class UserDto {

	private Integer id;
	
	private String username;
	private ProfileDto profile;
	private Timestamp joined;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
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
	 * @return the profile
	 */
	public ProfileDto getProfile() {
		return profile;
	}
	/**
	 * @param profile the profile to set
	 */
	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}
	/**
	 * @return the joined
	 */
	public Timestamp getJoined() {
		return joined;
	}
	/**
	 * @param joined the joined to set
	 */
	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}
	
	
	
}
