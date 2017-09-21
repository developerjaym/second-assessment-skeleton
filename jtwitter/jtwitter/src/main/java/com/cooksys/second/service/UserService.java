package com.cooksys.second.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.dto.NewUserDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.dto.UserDto;
import com.cooksys.second.entity.Uzer;
import com.cooksys.second.mapper.CredentialsMapper;
import com.cooksys.second.mapper.NewUserMapper;
import com.cooksys.second.mapper.ProfileMapper;
import com.cooksys.second.mapper.UserMapper;
import com.cooksys.second.repository.UserRepository;
import com.cooksys.second.repository.UzerJpaRepository;
import com.cooksys.second.utility.Parser;
import com.cooksys.second.utility.TimeStamper;


@Service
public class UserService {

	private UserRepository userRepository;
	private UzerJpaRepository uzerJpaRepository;
	private UserMapper userMapper;
	private NewUserMapper newUserMapper;
	private CredentialsMapper credentialsMapper;
	//mappers
	//other repositories
	private ProfileMapper profileMapper;
	
	private TweetService tweetService;
	
	public UserService(TweetService tweetService, UzerJpaRepository uzerJpaRepository, UserRepository userRepository, UserMapper userMapper, NewUserMapper newUserMapper, CredentialsMapper credentialsMapper, ProfileMapper profileMapper)
	{
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.newUserMapper = newUserMapper;
		this.credentialsMapper = credentialsMapper;
		this.uzerJpaRepository = uzerJpaRepository;
		this.tweetService = tweetService;
	}
	
	public List<UserDto> getUsers() {
		// TODO Auto-generated method stub
		return userMapper.toDtos(userRepository.getAllUsers().stream().filter(userdto->userdto.isActive()).collect(Collectors.toList()));
		//return null;
	}

	public List<UserDto> getEvenDeletedUsers() {
		// TODO Auto-generated method stub
		return userMapper.toDtos(userRepository.getAllUsers());//.stream().filter(userdto->userdto.isActive()).collect(Collectors.toList()));
		//return null;
	}
	
	public UserDto getUser(String username)
	{
		return userMapper.toDto(uzerJpaRepository.findByCredentialsUsername(username));
		//return userMapper.toDto(userRepository.get(username));
	}
	
	public UserDto createUser(NewUserDto newUserDto) {
		
		System.out.println("My new user dto is : " + newUserDto);
		Uzer uzer = newUserMapper.toUser(newUserDto);
		
		/*Try to handle this in a no args constructor*/
		uzer.setActive(true);
		uzer.setJoined(TimeStamper.getTimestamp());
		uzer.setCredentials(credentialsMapper.toCredentials(newUserDto.getCredentials()));
		uzer.setFollowedBy(new ArrayList<Uzer>());
		uzer.setFollowers(new ArrayList<Uzer>());
		
		uzer = userRepository.create(uzer);
		
		
		UserDto userDto = userMapper.toDto(uzer);
		userDto.setUsername(uzer.getCredentials().getUsername());//should be handled automatically
		
		return userDto;//userMapper.toDto(uzer);
	}

	public UserDto deleteUser(String username, CredentialsDto credentialsDto) {
		//set this Uzer's isActive field to false
		
		//make sure the password matches
		return userMapper.toDto(userRepository.delete(uzerJpaRepository.findByCredentialsUsername(username)));
	}

	public UserDto updateUser(String username, NewUserDto newUserDto) {
		//if successful return user with updated data
		
		//I think I need to go through each property of the new profile, if it's null, ignore it; if not, change the uzer
		
		return userMapper.toDto(userRepository.updateUser(uzerJpaRepository.findByCredentialsUsername(username), profileMapper.toProfile(newUserDto.getProfile())));
		
	}

	public void followUser(String username, CredentialsDto credentialsDto) {
		// TODO Auto-generated method stub
		//make the guy with these credentials follow the guy with this username
		//make sure the password is right
		userRepository.followUser(uzerJpaRepository.findByCredentialsUsername(username), uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername()));
		
	}

	public void unfollowUser(String username, CredentialsDto credentialsDto) {
		//make the guy with these credentials unfollow the guy with this username
				//make sure the password is right
		userRepository.unfollowUser(uzerJpaRepository.findByCredentialsUsername(username), uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername()));
				
	}

	public List<UserDto> getFollowers(String username) {
		//if successful, return ['User']
		Uzer user = uzerJpaRepository.findByCredentialsUsername(username);
		return userMapper.toDtos(user.getFollowedBy());
	}

	public List<UserDto> getFollowing(String username) {
		// TODO Auto-generated method stub
		Uzer user = uzerJpaRepository.findByCredentialsUsername(username);
		return userMapper.toDtos(user.getFollowers());
	}

	public List<TweetDto> getFeed(String username) {
		
		//retrieves all (non-deleted) tweets authored by the user with the given username
				//as well as all tweets authored by the users the given user is following
					//including simple tweets, reposts, and replies
				//the tweets should appear in reverse-chronological order
		/*ArrayList<TweetDto> list = new ArrayList<>();		
		list.addAll(userRepository.getTweetsFrom(uzerJpaRepository.findByCredentialsUsername(username)));
		
		return list;*/
		
		//use the tweetservice to do this
			//findTweetsWithSomeAuthor
		
		//look through all tweets, find ones authored by this guy or the guys followed by this guy
		List<TweetDto> list =  tweetService.getTweets().stream().filter(tweetDto-> tweetDto.getAuthor().getUsername().equals(username) || getFollowing(username).contains(tweetDto.getAuthor())).collect(Collectors.toList());
		list.sort(null);
		
		return list;
		
		//return null;
	}

	public List<TweetDto> getTweetsBy(String username) {
		
		//retrieves all (non-deleted) tweets authored by the user with the given username
				//this includes all three kinds of tweets
				//tweets should appear in reverse-chronological order
		
		//don't forget to sort it
		List<TweetDto> list = tweetService.getTweets().stream().filter(tweetDto->tweetDto.getAuthor().getUsername().equals(username)).collect(Collectors.toList());
		list.sort(null);
		return list;
	}

	public List<TweetDto> getMentions(String username) {
		//retrieves all non-deleted tweets in which the user with the username is mentioned
				//tweets should appear in reverse-chronological order
				//@username in a tweet's content means the user is mentioned
				//only tweets with content can mention someone
		
		List<TweetDto> list = tweetService.getTweets().stream().filter(tweetDto->Parser.mentionsName(username, tweetDto.getContent())).collect(Collectors.toList());
		list.sort(null);
		return list;
	}

}
