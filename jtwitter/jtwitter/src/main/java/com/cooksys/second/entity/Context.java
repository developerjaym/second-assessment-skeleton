package com.cooksys.second.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Context {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private Integer tweetId;
	/*@OneToOne
	private Tweet target;*/
	
	
	@OneToMany
	private Set<Tweet> before;//check annotation
	
	@OneToMany
	private Set<Tweet> after;//check annotation

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTweetId() {
		return tweetId;
	}

	public void setTweetId(Integer tweetId) {
		this.tweetId = tweetId;
	}

	public Set<Tweet> getBefore() {
		return before;
	}

	public void setBefore(Set<Tweet> before) {
		this.before = before;
	}

	public Set<Tweet> getAfter() {
		return after;
	}

	public void setAfter(Set<Tweet> after) {
		this.after = after;
	}
	
	
}
