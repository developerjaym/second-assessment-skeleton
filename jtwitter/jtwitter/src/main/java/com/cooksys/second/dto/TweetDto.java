package com.cooksys.second.dto;

import java.sql.Timestamp;

import com.cooksys.second.entity.Tweet;
import com.cooksys.second.entity.Uzer;

public class TweetDto {

	private Integer id;
	
	private Uzer author;
	
	private Timestamp posted;
	
	private String content;//optional
	
	private Tweet inReplyTo;//optional
	
	private Tweet repostOf;//optional

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Uzer getAuthor() {
		return author;
	}

	public void setAuthor(Uzer author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Tweet getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}
	
	
}
