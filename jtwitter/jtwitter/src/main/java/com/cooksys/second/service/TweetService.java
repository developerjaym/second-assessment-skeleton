package com.cooksys.second.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.ContextDto;
import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.dto.PostTweetDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.dto.UserDto;
import com.cooksys.second.entity.Hashtag;
import com.cooksys.second.entity.Tweet;
import com.cooksys.second.entity.Uzer;
import com.cooksys.second.mapper.ContextMapper;
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
	private ContextMapper contextMapper;
	
	
	public TweetService(ContextMapper contextMapper, UserService userService, TagMapper tagMapper, TweetJpaRepository tweetJpaRepository, UzerJpaRepository uzerJpaRepository, TweetRepository tweetRepository, TweetMapper tweetMapper)
	{
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.tagMapper = tagMapper;
		this.uzerJpaRepository = uzerJpaRepository;
		this.tweetJpaRepository = tweetJpaRepository;
		this.userService = userService;
		this.contextMapper = contextMapper;
	}
	
	public List<TweetDto> getTweets() {
		List<TweetDto> list = tweetMapper.toDtos(tweetRepository.getTweets());
		list.sort(null);
		return list;
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

	public List<TweetDto> getReplies(Integer id) {
		//retrieves the DIRECT replies to the tweet with the given id
				//if that tweet is deleted or otherwise doesn't exit
					//send an error
				//deleted replies to the tweet should be excluded from the response
				
				//return ['Tweet']
		
		//go through each non-deleted tweet
		//find ones that inReplyToId matches the given id
		
		List<TweetDto> list = getTweets().stream().filter(tweetDto->tweetDto.getInReplyTo().getId().equals(id)).collect(Collectors.toList());
		
		return list;
	}

	public List<TweetDto> getReposts(Integer id) {
		//retrieves the direct reposts of the tweet with the given id
		
				//if that tweet is deleted or doesn't exist
					//send error
				
				//response: ['Tweet']
		
		List<TweetDto> list = getTweets().stream().filter(tweetDto->tweetDto.getRepostOf().getId().equals(id)).collect(Collectors.toList());
		
		return list;
	}

	public List<UserDto> getMentions(Integer id) {
		//retrieves the users mentioned in the tweet with the given id
		
				//if that tweet is deleted or otherwise doesn't exist
					//send error
				
				//deleted users should be excluded from the response
				
				//IMPORTANT: remember that tags and mentions must be parsed by the server
				
		TweetDto tweetDto = this.getTweet(id);
		List<String> mentionedNames =  Parser.getNames(tweetDto.getContent());
		return userService.getUsers().stream().filter(userDto->mentionedNames.contains(userDto.getUsername())).collect(Collectors.toList());
		 
	}

	public ContextDto getContext(Integer id) {
		//retrieves the context of the tweet with the given id
		
				//if that tweet is deleted or doesn't exist
					//send error
				
				//IMPORTANT: deleted tweets should not be included in the context
					//but their replies should be included
		
		Tweet tweet = tweetJpaRepository.findById(id);
		ContextDto contextDto = contextMapper.toDto(tweet.getContext());
		//now remove all deleted tweets
		contextDto.setAfter(contextDto.getAfter().stream().filter(tweetDto-> tweetJpaRepository.findById(id).getActive()).collect(Collectors.toList()));
		contextDto.setBefore(contextDto.getBefore().stream().filter(tweetDto-> tweetJpaRepository.findById(id).getActive()).collect(Collectors.toList()));
		contextDto.setTarget(getTweet(id));//just to be sure
		
		return contextDto;
		
		
	}

	public TweetDto createRepost(Integer id, CredentialsDto credentialsDto) {
		//creates a repost of the tweet with the given id
				//the author of the repost should match the credentials provided in the body
				
				//if the given tweet is deleted or doesn't exist
				//or if the credentials match no one
					//send error
				
				//respond with the newly created tweet
		
		//Tweet originalTweet = tweetJpaRepository.findById(id);
		Tweet reposted = new Tweet();
		Uzer reposter = uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername());
		reposted.setType(TweetTypes.REPOST.toString());
		reposted.setRepostOfId(id);
		reposted.setContent("");
		reposted.setActive(true);
		reposted.setAuthor(reposter);
		reposted.setPosted(TimeStamper.getTimestamp());
		return tweetMapper.toDto(tweetRepository.createRepost(reposted));
		
	}

	public TweetDto createReply(Integer id, PostTweetDto newTweetDto) {
		//if the given tweet is deleted or doesn't exist
				//or if the given credentials match no one
					//send error
				
				//this creates a reply tweet, content is not optional
				//the server must create the inReplyTo property/relationship
				
				//the response should contain the newly created tweet
				
				//IMPORTANT: the server must process the tweet's contents for @{username} mentions
				//and #{hashtag} tags
		
		Tweet reply = new Tweet();
		Uzer replyer = uzerJpaRepository.findByCredentialsUsername(newTweetDto.getCredentials().getUsername());
		reply.setType(TweetTypes.REPLY.toString());
		reply.setInReplyToId(id);
		reply.setContent(newTweetDto.getContent());
		reply.setActive(true);
		reply.setAuthor(replyer);
		reply.setPosted(TimeStamper.getTimestamp());
		return tweetMapper.toDto(tweetRepository.createReply(reply, tweetJpaRepository.findById(id)));
	}
	
	
	
}
