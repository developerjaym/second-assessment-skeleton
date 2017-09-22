package com.cooksys.second.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	public List<Tweet> getRealTweets() {
		List<Tweet> list = tweetRepository.getTweets();
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
	public boolean isTweetActiveAndExisting(Integer id)
	{//active and existing
		return getTweets().stream().filter(tweetDto->tweetDto.getId().equals(id)).collect(Collectors.toList()).size() == 1;
	}
	public TweetDto getTweet(Integer id) 
	{//if time, find more efficient way to do this (does findById return null if no such tweet exists?)
		
		if(isTweetActiveAndExisting(id))
			return tweetMapper.toDto(tweetJpaRepository.findById(id));
		return null;//inactive
	}

	public TweetDto deleteTweet(Integer id, CredentialsDto credentialsDto) {
		
		//check credentials
		
		//Uzer user = uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername());
		
		if(!isTweetActiveAndExisting(id) || !credentialsMatch(credentialsDto))
			return null;
		
		Tweet tweet = tweetJpaRepository.findById(id);
		tweetRepository.deleteTweet(tweet);
		
		return tweetMapper.toDto(tweet);
	}

	public boolean likeTweet(Integer id, CredentialsDto credentialsDto) {
		if(!credentialsMatch(credentialsDto))
			return false;
		Tweet tweet = tweetJpaRepository.findById(id);
		ArrayList<Integer> list = (ArrayList<Integer>) Arrays.asList(tweet.getLikedBy());
		list.add(uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername()).getId());
		tweet.setLikedBy(list.toArray(tweet.getLikedBy()));
		return true;
	}

	public List<HashtagDto> getTags(Integer id) 
	{
		if(!isTweetActiveAndExisting(id))
			return null;
		
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
		if(!isTweetActiveAndExisting(id))
			return null;
		
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
		
		//go through each non-deleted tweet
		//find ones that inReplyToId matches the given id
		if(!isTweetActiveAndExisting(id))
			return null;
		
		List<TweetDto> list = getTweets().stream().filter(tweetDto->tweetDto.getInReplyTo().equals(id)).collect(Collectors.toList());
		
		return list;
	}

	public List<TweetDto> getReposts(Integer id) {
		if(!isTweetActiveAndExisting(id))
			return null;
		List<TweetDto> list = getTweets().stream().filter(tweetDto->tweetDto.getRepostOf().equals(id)).collect(Collectors.toList());
		
		return list;
	}

	public List<UserDto> getMentions(Integer id) {
		if(!isTweetActiveAndExisting(id))
			return null;
				
		TweetDto tweetDto = this.getTweet(id);
		List<String> mentionedNames =  Parser.getNames(tweetDto.getContent());
		return userService.getUsers().stream().filter(userDto->mentionedNames.contains(userDto.getUsername())).collect(Collectors.toList());
		 
	}

	public ContextDto getContext(Integer id) {
		if(!isTweetActiveAndExisting(id))
			return null;
		
		Tweet tweet = tweetJpaRepository.findById(id);
		//ContextDto contextDto = contextMapper.toDto(tweet.getContext());
		ContextDto contextDto = contextMapper.toDto(tweetRepository.get(tweet.getContextId()));
		//now remove all deleted tweets
		
		//do array stuff for now
		ArrayList<Integer> after = new ArrayList<>();
		for(Integer i : contextDto.getAfter())
			after.add(i);
		ArrayList<Integer> before = new ArrayList<>();
		for(Integer i : contextDto.getBefore())
			before.add(i);
		
		contextDto.setAfter(after.stream().filter(tweetDto-> tweetJpaRepository.findById(id).getActive()).collect(Collectors.toList()).toArray(contextDto.getAfter()));
		contextDto.setBefore(before.stream().filter(tweetDto-> tweetJpaRepository.findById(id).getActive()).collect(Collectors.toList()).toArray(contextDto.getBefore()));
		contextDto.setTarget(id);//getTweet(id));//just to be sure
		
		return contextDto;
		
		
	}

	public TweetDto createRepost(Integer id, CredentialsDto credentialsDto) {
		
		if(!credentialsMatch(credentialsDto))
			return null;
		if(!isTweetActiveAndExisting(id))
			return null;
		
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

	public TweetDto createReply(Integer id, PostTweetDto newTweetDto) 
	{
		if(!credentialsMatch(newTweetDto.getCredentials()))
			return null;
		if(!isTweetActiveAndExisting(id))
			return null;
		
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

	public boolean credentialsMatch(CredentialsDto credentials) {
		//go through all Uzers, if one with these credentials is active, return true
		return userService.getUzers().stream().filter(uzer->uzer.getCredentials().getUsername().equals(credentials.getUsername()) && uzer.getCredentials().getPassword().equals(credentials.getPassword())).collect(Collectors.toList()).size()==1;
	}

	public void reactivateTweetsBy(Uzer author) {
		tweetRepository.reactivateTweetsBy(author);
	}

	
	
	
	
}
