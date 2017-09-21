package com.cooksys.second.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.dto.PostTweetDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.dto.UserDto;
import com.cooksys.second.entity.Hashtag;
import com.cooksys.second.entity.Tweet;
import com.cooksys.second.entity.Uzer;
import com.cooksys.second.mapper.TagMapper;
import com.cooksys.second.mapper.TweetMapper;
import com.cooksys.second.repository.TweetJpaRepository;
import com.cooksys.second.repository.TweetRepository;
import com.cooksys.second.repository.UzerJpaRepository;
import com.cooksys.second.utility.Parser;
import com.cooksys.second.utility.TimeStamper;
import com.cooksys.second.utility.TweetTypes;

@Service
public class TweetService {

	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private TagMapper tagMapper;
	private UzerJpaRepository uzerJpaRepository;
	private TweetJpaRepository tweetJpaRepository;
	private UserService userService;
	
	
	public TweetService(UserService userService, TagMapper tagMapper, TweetJpaRepository tweetJpaRepository, UzerJpaRepository uzerJpaRepository, TweetRepository tweetRepository, TweetMapper tweetMapper)
	{
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.tagMapper = tagMapper;
		this.uzerJpaRepository = uzerJpaRepository;
		this.tweetJpaRepository = tweetJpaRepository;
		this.userService = userService;
	}
	
	public List<TweetDto> getTweets() {
		//non-deleted tweets, reverse-chronological order
		
		
		//make sure the TweetDto only has the DTO version of each property
		List<TweetDto> list = tweetMapper.toDtos(tweetRepository.getTweets());
		
		list.sort(null);
		
		return list;
		
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
		
		//I should parse it for hashtags and then make new hashtags
		List<Hashtag> hashtags = Parser.getHashtags(postTweetDto.getContent());
		tweetRepository.createHashtags(hashtags);
		
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

	public List<HashtagDto> getTags(Integer id) {
		//retrieves the tags associated with the tweet with the given id
		
				//if that tweet is deleted or doesn't exist
					//send error
				//if successful, return ['Hashtag']
				
				//IMPORTANT: remember that tags and mentions must be parsed by the server
		
		//go through each tag in my database
			//check the tweet's contents for each tag
			//if we found it, add it to the List
		Tweet tweet = tweetJpaRepository.findById(id);
		String contents = tweet.getContent();
		List<HashtagDto> tagList = new ArrayList<HashtagDto>();
		tweetRepository.getAllHashtags().forEach((hashtag)->
		{
			if(Parser.containsTag(hashtag.getLabel(), contents))
				tagList.add(tagMapper.toDto(hashtag));
		});
		
		
		return tagList;
	}

	public Set<UserDto> getLikes(Integer id) {
		//go through each saved ID in the Tweet's likedBy array, find the corresponding uzer, set 'em up and return
		Integer[] likerIds =  tweetJpaRepository.findById(id).getLikedBy();
		Set<UserDto> likers = new HashSet<UserDto>();
		for(Integer i : likerIds)
		{
			likers.add(uzerJpaRepository.findById(i));
		}
		return likers;
	}
	
	
	
}
