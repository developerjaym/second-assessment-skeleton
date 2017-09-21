package com.cooksys.second.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.service.TagService;
import com.cooksys.second.service.TweetService;
import com.cooksys.second.utility.Parser;

@RestController
@RequestMapping("tags")
public class TagController {

	private TagService tagService;
	
	public TagController(TweetService tweetService, TagService tagService)
	{
		this.tagService = tagService;
	}
	@GetMapping()
	public List<HashtagDto> getTags()
	{
		return tagService.getAllTags();
	}
	@GetMapping("{label}")
	public List<TweetDto> getTweetsTaggedSo(@PathVariable String label, HttpServletResponse response)
	{
		response.setStatus(200);
		List<TweetDto> tweetsTaggedSo = tagService.getTweetsTaggedSo(label);
		if(tweetsTaggedSo.isEmpty())
			response.setStatus(204);//no content
		return tweetsTaggedSo;
	}
	
}
