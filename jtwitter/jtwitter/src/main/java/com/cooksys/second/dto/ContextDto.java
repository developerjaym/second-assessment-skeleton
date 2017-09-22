package com.cooksys.second.dto;

import java.util.List;


public class ContextDto {
	
	private Integer target;
	private Integer[] before;
	private Integer[] after;
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
	public TweetDto getTarget() {
		return target;
	}
	public void setTarget(TweetDto target) {
		this.target = target;
	}
	
	public List<TweetDto> getBefore() {
		return before;
	}
	public void setBefore(List<TweetDto> before) {
		this.before = before;
	}
	public List<TweetDto> getAfter() {
		return after;
	}
	public void setAfter(List<TweetDto> after) {
		this.after = after;
	}
	*/
	
}
