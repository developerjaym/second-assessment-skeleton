package com.cooksys.second.entity;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Hashtag {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
	
}
