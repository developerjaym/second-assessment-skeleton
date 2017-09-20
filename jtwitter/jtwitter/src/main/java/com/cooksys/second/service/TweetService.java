package com.cooksys.second.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.mapper.TweetMapper;
import com.cooksys.second.repository.TweetRepository;

@Service
public class TweetService {

	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	public TweetService(TweetRepository tweetRepository, TweetMapper tweetMapper)
	{
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}
	public List<TweetDto> getTweets() {
		//non-deleted tweets, reverse-chronological order
		
		return tweetMapper.toDtos(tweetRepository.getTweets());
		
		//return null;
	}
	
}
