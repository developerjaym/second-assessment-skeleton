package com.cooksys.second.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second.dto.ContextDto;
import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.dto.PostTweetDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.dto.UserDto;
import com.cooksys.second.service.TweetService;

@RestController
@RequestMapping("tweets")
public class TweetController {

	private TweetService tweetService;
	
	public TweetController(TweetService tweetService)
	{
		this.tweetService = tweetService;
	}
	
	@GetMapping()
	public List<TweetDto> getTweets(HttpServletResponse response)
	{
		response.setStatus(200);
		return tweetService.getTweets();
	}
	@PostMapping
	public TweetDto createSimpleTweet(@RequestBody PostTweetDto postTweetDto, HttpServletResponse response)
	{
		if(tweetService.credentialsMatch(postTweetDto.getCredentials()))
			return tweetService.createSimpleTweet(postTweetDto);
		else
			response.setStatus(401);//unauthorized
		return null;
	}
	@GetMapping("{id}")
	public TweetDto getTweet(@PathVariable Integer id, HttpServletResponse response)
	{
		TweetDto tweet = tweetService.getTweet(id);
		if(tweet == null)
			response.setStatus(404);
		return tweet;
	}
	@DeleteMapping("{id}")
	public TweetDto deleteTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response)
	{//can a delete have a requestbody?
		TweetDto tweet = tweetService.deleteTweet(id, credentialsDto);
		if(tweet == null)
			response.setStatus(404);
		return tweet;
	}
	@PostMapping("{id}/like")
	public void likeTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response)
	{
		if(!tweetService.likeTweet(id, credentialsDto))
			response.setStatus(404);
	}
	@PostMapping("{id}/reply")
	public TweetDto createReply(@PathVariable Integer id, @RequestBody PostTweetDto newTweetDto, HttpServletResponse response)
	{
		TweetDto tweet = tweetService.createReply(id, newTweetDto);
		if(tweet == null)
			response.setStatus(404);
		return tweet;
	}
	@PostMapping("{id}/repost")
	public TweetDto createRepost(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response)
	{
		TweetDto tweet = tweetService.createRepost(id, credentialsDto);
		if(tweet == null)
			response.setStatus(404);
		return tweet;
	}
	@GetMapping("{id}/tags")
	public List<HashtagDto> getTags(@PathVariable Integer id, HttpServletResponse response)
	{
		List<HashtagDto> list = tweetService.getTags(id);
		if(list == null)
			response.setStatus(404);
		else if(list.isEmpty())
			response.setStatus(404);
		return list;
	}
	@GetMapping("{id}/likes")
	public Set<UserDto> getLikes(@PathVariable Integer id, HttpServletResponse response)
	{
		Set<UserDto> set = tweetService.getLikes(id);
		if(set == null)
			response.setStatus(404);
		return set;
	}
	@GetMapping("{id}/context")	
	public ContextDto getContext(@PathVariable Integer id, HttpServletResponse response)
	{
		ContextDto contextDto = tweetService.getContext(id);
		if(contextDto == null)
			response.setStatus(404);
		return contextDto;
	}
	@GetMapping("{id}/replies")
	public List<TweetDto> getReplies(@PathVariable Integer id, HttpServletResponse response)
	{
		List<TweetDto> list = tweetService.getReplies(id);
		if(list == null)
			response.setStatus(404);
		else if(list.isEmpty())
			response.setStatus(404);
		return list;
	}
	@GetMapping("{id}/reposts")
	public List<TweetDto> getReposts(@PathVariable Integer id, HttpServletResponse response)
	{
		List<TweetDto> list = tweetService.getReposts(id);
		if(list == null)
			response.setStatus(404);
		else if(list.isEmpty())
			response.setStatus(404);
		return list;
	}
	@GetMapping("{id}/mentions")
	public List<UserDto> getMentions(@PathVariable Integer id, HttpServletResponse response)
	{
		List<UserDto> list = tweetService.getMentions(id);
		if(list == null)
			response.setStatus(404);
		else if(list.isEmpty())
			response.setStatus(404);
		return list;
	}
	
}
