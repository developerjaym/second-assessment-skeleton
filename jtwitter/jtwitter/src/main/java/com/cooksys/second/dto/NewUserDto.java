package com.cooksys.second.dto;

public class NewUserDto {
	private CredentialsDto credentials;
	private ProfileDto profile;
	/**
	 * @return the credentials
	 */
	public CredentialsDto getCredentials() {
		return credentials;
	}
	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(CredentialsDto credentials) {
		this.credentials = credentials;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NewUserDto [credentials=" + credentials + ", profile=" + profile + "]";
	}
	
	
}
