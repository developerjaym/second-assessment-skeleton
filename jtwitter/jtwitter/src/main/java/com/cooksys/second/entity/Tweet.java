package com.cooksys.second.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Tweet {
	@Id
	@GeneratedValue
	private Integer id;
	
	/*@Column(nullable = false)
	private Integer authorId;*/
	
	@OneToOne
	private Uzer author;
	
	@Column(nullable = false)
	private Timestamp posted;
	
	
	@Column(nullable = false)
	private /*final*/ String type;//simple, repost, or reply
	
	/*
	private Set<User> likedBy;
	private Set<Hashtag> tags;
	private Set<User> mentions;
	*/
	
	@Column(nullable = false)
	private boolean isActive;
	
	@Column(nullable = true)
	private String content;
	@Column(nullable = true)
	private Integer inReplyToId;
	//private Tweet inReplyTo;
	@Column(nullable = true)
	private Integer repostOfId;
	//private Tweet repostOf;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getInReplyToId() {
		return inReplyToId;
	}
	public void setInReplyToId(Integer inReplyToId) {
		this.inReplyToId = inReplyToId;
	}
	public Integer getRepostOfId() {
		return repostOfId;
	}
	public void setRepostOfId(Integer repostOfId) {
		this.repostOfId = repostOfId;
	}
	
}

