package com.cooksys.second.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.PostTweetDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.entity.Tweet;
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
	
	
	
}
