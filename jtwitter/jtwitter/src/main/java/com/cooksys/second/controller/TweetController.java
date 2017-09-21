package com.cooksys.second.controller;

import java.util.List;
import java.util.Set;

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
import com.cooksys.second.dto.NewSimpleTweetDto;
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
	public List<TweetDto> getTweets()
	{//consider a different return type
		
		//retrieves all non-deleted tweets
		//the tweets should appear in reverse-chronological order
		//response: ['tweet']
		
		
		return tweetService.getTweets();
		
		//return null;
	}
	@PostMapping
	public TweetDto createSimpleTweet(@RequestBody PostTweetDto postTweetDto)
	{
		//creates a new SIMPLE tweet
		//sets author to the user identified by the credentials in requestbody
		
		//if the credentials do not match an active user
			//send error
		
		//response should contain newly-created tweet
			//it must not have a 'inreplyto' or 'repost' properties
		
		tweetService.createSimpleTweet(postTweetDto);
		
		return null;
	}
	@GetMapping("{id}")
	public TweetDto getTweet(@PathVariable Integer id)
	{
		//retrieves a tweet with the given id
		
		//if no such tweet or the tweet is deleted
			//send error
		
		//response 'Tweet'
		
		return tweetService.getTweet(id);
	}
	@DeleteMapping("{id}")
	public TweetDto deleteTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto)
	{//can a delete have a requestbody?
		//deletes the tweet with the given id
		
		//if no such tweet exists or the credentials do not match the author
			//send error
		
		//if tweet is deleted,
			//respond with the tweet data prior to deletion
		
		//IMPORTANT: don't drop these tweets from the database
			//replies and reposts and those relationships should remain intact
		
		return tweetService.deleteTweet(id, credentialsDto);
		
		//return null;
	}
	@PostMapping("{id}/like")
	public void likeTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto)
	{
		//creates a like relationship between th given id and the user whose credentials are provided
		
		//if the tweet is deleted or doesn't exist
		//or if the given credentials don't match anyone
			//send error
		//if successful, no response body is sent
		
		tweetService.likeTweet(id, credentialsDto);
	}
	@PostMapping("{id}/reply")
	public TweetDto createReply(@PathVariable Integer id, @RequestBody NewSimpleTweetDto newTweetDto)
	{
		//if the given tweet is deleted or doesn't exist
		//or if the given credentials match no one
			//send error
		
		//this creates a reply tweet, content is not optional
		//the server must create the inReplyTo property/relationship
		
		//the response should contain the newly created tweet
		
		//IMPORTANT: the server must process the tweet's contents for @{username} mentions
		//and #{hashtag} tags
		
		return null;
	}
	@PostMapping("{id}/repost")
	public TweetDto createRepost(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto)
	{
		//creates a repost of the tweet with the given id
		//the author of the repost should match the credentials provided in the body
		
		//if the given tweet is deleted or doesn't exist
		//or if the credentials match no one
			//send error
		
		//respond with the newly created tweet
		
		
		return null;
	}
	@GetMapping("{id}/tags")
	public Set<HashtagDto> getTags(@PathVariable Integer id)
	{
		//retrieves the tags associated with the tweet with the given id
		
		//if that tweet is deleted or doesn't exist
			//send error
		//if successful, return ['Hashtag']
		
		//IMPORTANT: remember that tags and mentions must be parsed by the server
		
		return tweetService.getTags(id);
	}
	@GetMapping("{id}/likes")
	public Set<UserDto> getLikes(@PathVariable Integer id)
	{
		//retrieves the active users who have liked this tweet with the given id
		
		//if that tweet is deleted or otherwise doesn't exist,
			//send error
		
		//deleted users should be excluded from the response
		
		//return ['User']
		
		return null;
	}
	@GetMapping("{id}/context")	
	public ContextDto getContext(@PathVariable Integer id)
	{
		//retrieves the context of the tweet with the given id
		
		//if that tweet is deleted or doesn't exist
			//send error
		
		//IMPORTANT: deleted tweets should not be included in the context
			//but their replies should be included
		
		return null;
	}
	@GetMapping("{id}/replies")
	public TweetDto[] getReplies(@PathVariable Integer id)
	{//consider a different return type
		
		//retrieves the DIRECT replies to the tweet with the given id
		//if that tweet is deleted or otherwise doesn't exit
			//send an error
		//deleted replies to the tweet should be excluded from the response
		
		//return ['Tweet']
		
		return null;
	}
	@GetMapping("{id}/reposts")
	public TweetDto[] getReposts(@PathVariable Integer id)
	{//consider a different return type
		//retrieves the direct reposts of the tweet with the given id
		
		//if that tweet is deleted or doesn't exist
			//send error
		
		//response: ['Tweet']
		
		return null;
	}
	@GetMapping("{id}/mentions")
	public Set<UserDto> getMentions(@PathVariable Integer id)
	{
		//retrieves the users mentioned in the tweet with the given id
		
		//if that tweet is deleted or otherwise doesn't exist
			//send error
		
		//deleted users should be excluded from the response
		
		//IMPORTANT: remember that tags and mentions must be parsed by the server
		return null;
	}
	
}
