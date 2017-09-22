package com.cooksys.second.entity;

import java.util.List;
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
	
	/*@Column(nullable = false)
	private Integer tweetId;*/
	//@OneToOne
	//private Tweet target;
	private Integer target;
	
	//@OneToMany
	//private List<Tweet> before;//check annotation
	private Integer[] before;
	
	//@OneToMany
	//private List<Tweet> after;//check annotation
	private Integer[] after;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer[] getBefore() {
		return before;
	}

	public void setBefore(Integer[] before) {
		this.before = before;
	}

	public Integer[] getAfter() {
		return after;
	}

	public void setAfter(Integer[] after) {
		this.after = after;
	}

/*
	public Tweet getTarget() {
		return target;
	}

	public void setTarget(Tweet target) {
		this.target = target;
	}

	public List<Tweet> getBefore() {
		return before;
	}

	public void setBefore(List<Tweet> before) {
		this.before = before;
	}

	public List<Tweet> getAfter() {
		return after;
	}

	public void setAfter(List<Tweet> after) {
		this.after = after;
	}
	*/
	
}
