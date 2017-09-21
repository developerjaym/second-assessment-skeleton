package com.cooksys.second.utility;

public class Parser {
/**
 * This will be for parsing Tweet content for #hashtags and @usernames
 */
	
	/**
	 * 
	 * @param label the hashtag (don't send the # symbol)
	 * @param contents the contents to be searched for the hashtag
	 * @return true if contents contains hashtag
	 */
	public static boolean containsTag(String label, String contents)
	{
		contents = contents + " ";//add whitespace at end for easier parsing
		String tag = "#" + label;
		if(contents.contains(tag))
			return true;
		if(contents.contains(tag + " "))
			return true;
		if(contents.contains(tag + ","))
			return true;
		if(contents.contains(tag + "."))
			return true;
		if(contents.contains(tag + ":"))
			return true;
		if(contents.contains(tag + "!"))
			return true;
		if(contents.contains(tag + "?"))
			return true;
		if(contents.contains(tag + "@"))
			return true;
		if(contents.contains(tag + "#"))
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param username the username, should have an @ symbol in front of it
	 * @param contents the contents to be searched for @ and username
	 * @return true if contents contains @ and username together
	 */
	public static boolean mentionsName(String username, String contents)
	{
		contents = contents + " ";//add whitespace at end for easier parsing
		String mention = "@" + username;
		if(contents.contains(mention + " "))
			return true;
		if(contents.contains(mention + ","))
			return true;
		if(contents.contains(mention + "."))
			return true;
		if(contents.contains(mention + ":"))
			return true;
		if(contents.contains(mention + "!"))
			return true;
		if(contents.contains(mention + "?"))
			return true;
		if(contents.contains(mention + "@"))
			return true;
		if(contents.contains(mention + "#"))
			return true;
		return false;	
	}
}
