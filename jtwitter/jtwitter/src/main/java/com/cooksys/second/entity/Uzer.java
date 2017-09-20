package com.cooksys.second.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Uzer {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/*@Column(unique=true, nullable = false)
	private String username;*/
	
	@Column(nullable = false)
	private Timestamp joined;
	
	@Column(nullable = false)
	private boolean isActive;
	
	@Embedded
	private Profile profile;
	
	@Embedded
	private Credentials credentials;//not to be part of the Dao
	
	/*
	@ManyToMany
	private Set<User> followers;
	
	@ManyToMany(mappedBy= "followers")
	private Set<User> followedBy;//check this later
	*/
	
	
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

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	/**
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

//	/**
//	 * @return the followers
//	 */
//	public Set<User> getFollowers() {
//		return followers;
//	}
//
//	/**
//	 * @param followers the followers to set
//	 */
//	public void setFollowers(Set<User> followers) {
//		this.followers = followers;
//	}
//
//	/**
//	 * @return the followedBy
//	 */
//	public Set<User> getFollowedBy() {
//		return followedBy;
//	}
//
//	/**
//	 * @param followedBy the followedBy to set
//	 */
//	public void setFollowedBy(Set<User> followedBy) {
//		this.followedBy = followedBy;
//	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Uzer other = (Uzer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", joined=" + joined + ", isActive=" + isActive + ", profile=" + profile
				+ ", credentials=" + credentials + "";////, followers=" + followers + ", followedBy=" + followedBy + "]";
	}
	
	
}
