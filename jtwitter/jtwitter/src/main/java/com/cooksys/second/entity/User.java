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
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	/*@Column(unique=true, nullable = false)
	private String username;*/
	
	@Column(nullable = false)
	private Timestamp joined;
	
	@Embedded
	private Profile profile;
	
	@Embedded
	private Credentials credentials;//not to be part of the Dao
	
	@ManyToMany
	private Set<User> followers;
	
	@ManyToMany(mappedBy= "followers")
	private Set<User> followedBy;//check this later
	
}
