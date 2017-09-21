package com.cooksys.second.utility;

import java.util.ArrayList;
import java.util.List;

import com.cooksys.second.entity.Hashtag;

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

	/**
	 * Somehow make a list of Hashtags found in the given content
	 * 
	 * TODO Test this
	 * 
	 * @param content
	 * @return
	 */
	public static List<Hashtag> getHashtags(String content) {
		ArrayList<Hashtag> list = new ArrayList<>();
		while(content.contains("#"))
		{
			content = content + " ";//add whitespace for convenien
			int firstTag = content.indexOf("#");
			int spaceIndex = content.indexOf(" ", firstTag);
			Hashtag tag = new Hashtag();
			tag.setLabel(content.substring(firstTag, spaceIndex));
			tag.setLastUsed(TimeStamper.getTimestamp());
			//tag.setFirstUsed???
			list.add(tag);
			content = content.substring(spaceIndex);
		}
		return list;
		
	}
}
