package com.cooksys.second.dto;

import java.sql.Timestamp;

import com.cooksys.second.entity.Tweet;
import com.cooksys.second.entity.Uzer;

public class TweetDto implements Comparable<TweetDto>
{

	private Integer id;
	
	private UserDto author;
	
	private Timestamp posted;
	
	private String content;//optional
	
	private Integer inReplyTo;//optional
	
	private Integer repostOf;//optional

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserDto getAuthor() {
		return author;
	}

	public void setAuthor(UserDto author) {
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

	public Integer getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Integer inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Integer getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Integer repostOf) {
		this.repostOf = repostOf;
	}

	@Override
	public int compareTo(TweetDto o) {
		return posted.compareTo(o.posted);//might be backwards
	}
	
	
}
