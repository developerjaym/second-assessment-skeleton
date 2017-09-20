package com.cooksys.second.utility;

public enum TweetTypes
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
