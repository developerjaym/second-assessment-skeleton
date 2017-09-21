package com.cooksys.second.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.dto.PostTweetDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.entity.Tweet;
import com.cooksys.second.entity.Uzer;
import com.cooksys.second.mapper.TweetMapper;
import com.cooksys.second.repository.TweetJpaRepository;
import com.cooksys.second.repository.TweetRepository;
import com.cooksys.second.repository.UzerJpaRepository;
import com.cooksys.second.utility.TimeStamper;
import com.cooksys.second.utility.TweetTypes;

@Service
public class TweetService {

	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private UzerJpaRepository uzerJpaRepository;
	private TweetJpaRepository tweetJpaRepository;
	
	
	public TweetService(TweetJpaRepository tweetJpaRepository, UzerJpaRepository uzerJpaRepository, TweetRepository tweetRepository, TweetMapper tweetMapper)
	{
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.uzerJpaRepository = uzerJpaRepository;
		this.tweetJpaRepository = tweetJpaRepository;
	}
	
	public List<TweetDto> getTweets() {
		//non-deleted tweets, reverse-chronological order
		
		
		//make sure the TweetDto only has the DTO version of each property
		return tweetMapper.toDtos(tweetRepository.getTweets());
		
		
		//return null;
	}
	public TweetDto createSimpleTweet(PostTweetDto postTweetDto) {
		//should return the newly-made Tweet
		
		//postTweetDto has content (string) and Credentials
			//make sure the credentials match
			//make sure the user is active
		//figure out how to map this, make your own mapper if necessary
		Tweet tweet = new Tweet();
		tweet.setActive(true);
		tweet.setAuthor(uzerJpaRepository.findByCredentialsUsername(postTweetDto.getCredentials().getUsername()));
		tweet.setContent(postTweetDto.getContent());
		tweet.setPosted(TimeStamper.getTimestamp());
		tweet.setType(TweetTypes.SIMPLE.toString());
		
		return tweetMapper.toDto(tweetRepository.createTweet(tweet));
		
		
	}

	public TweetDto getTweet(Integer id) {
		// TODO Auto-generated method stub
		return tweetMapper.toDto(tweetJpaRepository.findById(id));
	}

	public TweetDto deleteTweet(Integer id, CredentialsDto credentialsDto) {
		
		//check credentials
		
		//Uzer user = uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername());
		
		
		Tweet tweet = tweetJpaRepository.findById(id);
		tweetRepository.deleteTweet(tweet);
		
		return tweetMapper.toDto(tweet);
	}

	public void likeTweet(Integer id, CredentialsDto credentialsDto) {
		Tweet tweet = tweetJpaRepository.findById(id);
		ArrayList<Integer> list = (ArrayList<Integer>) Arrays.asList(tweet.getLikedBy());
		list.add(uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername()).getId());
		tweet.setLikedBy(list.toArray(tweet.getLikedBy()));
		
	}

	public Set<HashtagDto> getTags(Integer id) {
		//retrieves the tags associated with the tweet with the given id
		
				//if that tweet is deleted or doesn't exist
					//send error
				//if successful, return ['Hashtag']
				
				//IMPORTANT: remember that tags and mentions must be parsed by the server
		
		//go through each tag in my database
			//check the tweet's contents for each tag
		return null;
	}
	
	
	
}
