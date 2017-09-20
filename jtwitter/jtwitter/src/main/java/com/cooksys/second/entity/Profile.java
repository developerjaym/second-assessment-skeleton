package com.cooksys.second.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Profile {
	
	@Column(nullable = true)
	private String firstName;
	@Column(nullable = true)
	private String lastName;
	@Column(nullable = false)
	private String email;
	@Column(nullable = true)
	private String phone;
}
