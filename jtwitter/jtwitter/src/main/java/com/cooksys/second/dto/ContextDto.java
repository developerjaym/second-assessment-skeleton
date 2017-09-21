package com.cooksys.second.dto;

import java.util.List;


public class ContextDto {
	
	private TweetDto target;
	private List<TweetDto> before;
	private List<TweetDto> after;
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
	
	
}
