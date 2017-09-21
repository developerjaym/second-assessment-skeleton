package com.cooksys.second.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.service.TagService;

@RestController
@RequestMapping("tags")
public class TagController {

	private TagService tagService;
	
	public TagController(TagService tagService)
	{
		this.tagService = tagService;
	}
	@GetMapping()
	public List<HashtagDto> getTags()
	{
		//retrieves all Hashtags tracked by the database
		
		//returns ['Hashtag']
		//return null;
		return tagService.getAllTags();
	}
	@GetMapping("{label}")
	public TweetDto[] getTweetsTaggedSo(@PathVariable String label)
	{
		//retrieves all non-deleted tweets tagged with the given hashtag label
		//the tweets should appear in reverse-chronological order
		
		//a tweet is considered 'tagged' by the hashtag
			//if the tweet has content
			//and if the hashtag's label appears in that content following a #
		
		//if no such hashtag exists
			//send error
		
		
		return null;
	}
	
}
