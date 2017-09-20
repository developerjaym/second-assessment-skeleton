package com.cooksys.second.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tweet {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private Integer authorId;
	//private User author;
	
	@Column(nullable = false)
	private Timestamp posted;
	
	
	@Column(nullable = false)
	private /*final*/ String type;//simple, repost, or reply
	
	/*
	private Set<User> likedBy;
	private Set<Hashtag> tags;
	private Set<User> mentions;
	*/
	
	@Column(nullable = true)
	private String content;
	@Column(nullable = true)
	private Integer inReplyToId;
	//private Tweet inReplyTo;
	@Column(nullable = true)
	private Integer repostOfId;
	//private Tweet repostOf;
	
}

