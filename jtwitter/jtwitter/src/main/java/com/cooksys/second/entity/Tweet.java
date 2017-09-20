package com.cooksys.second.entity;

import java.sql.Timestamp;

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
	private User author;
	
	@Column(nullable = false)
	private Timestamp posted;
	
	
	@Column(nullable = false)
	private /*final*/ String TYPE;//simple, repost, or reply
	
	
	
	@Column(nullable = true)
	private String content;
	@Column(nullable = true)
	private Tweet inReplyTo;
	@Column(nullable = true)
	private Tweet repostOf;
	
}

enum TweetTypes
{
	SIMPLE("Simple"),
	REPOST("Repost"),
	REPLY("Reply");
	private final String string;
	private TweetTypes(String string)
	{
		this.string = string;
	}
	@Override
	public String toString()
	{
		return string;
	}
}