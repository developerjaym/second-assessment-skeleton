package com.cooksys.second.service;


import java.util.ArrayList;
import java.util.List;
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
import com.cooksys.second.mapper.TweetMapper;
import com.cooksys.second.mapper.UserMapper;
import com.cooksys.second.repository.CredentialsRepository;
import com.cooksys.second.repository.TweetRepository;
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
	
	//private TweetService tweetService;
	private ValidationService validationService;
	private CredentialsRepository credentialsRepository;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	public UserService(TweetMapper tweetMapper, TweetRepository tweetRepository, ValidationService validationService, /*TweetService tweetService*/CredentialsRepository credentialsRepository, UzerJpaRepository uzerJpaRepository, UserRepository userRepository, UserMapper userMapper, NewUserMapper newUserMapper, CredentialsMapper credentialsMapper, ProfileMapper profileMapper)
	{
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.newUserMapper = newUserMapper;
		this.credentialsMapper = credentialsMapper;
		this.uzerJpaRepository = uzerJpaRepository;
		//this.tweetService = tweetService;
		this.credentialsRepository = credentialsRepository;
		this.validationService = validationService;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.profileMapper = profileMapper;
	}
	
	public List<UserDto> getUsers() {
		return userMapper.toDtos(userRepository.getAllUsers().stream().filter(userdto->userdto.isActive()).collect(Collectors.toList()));
		//return null;
	}

	public List<Uzer> getUzers()
	{
		return userRepository.getAllUsers().stream().filter(uzer->uzer.isActive()).collect(Collectors.toList());
	}
	
	public List<UserDto> getEvenDeletedUsers() {
		return userMapper.toDtos(userRepository.getAllUsers());//.stream().filter(userdto->userdto.isActive()).collect(Collectors.toList()));
		//return null;
	}
	public boolean userExistsAndIsActive(String username)
	{
		/*List<UserDto> list = getUsers()
				.stream()
				.filter(userDto->
					userDto.getUsername().equals(username)
				)
				.collect(Collectors.toList());*/
		List<Uzer> list = userRepository.getAllUsers()
				.stream()
				.filter(uzer->
					uzer.getCredentials().getUsername().equals(username) && uzer.isActive()
				)
				.collect(Collectors.toList());
		if(list == null)
			return false;
		if(list.isEmpty())
			return false;
		return true;
	}
	public UserDto getUser(String username)
	{
		if(!userExistsAndIsActive(username))
			return null;
		return userMapper.toDto(uzerJpaRepository.findByCredentialsUsername(username));
	}
	
	public UserDto createUser(NewUserDto newUserDto) {
		
		if(credentialsMatch(newUserDto.getCredentials()))
		{
			System.out.println("REANIMATING");
			//reactivate
			Uzer uzer = uzerJpaRepository.findByCredentialsUsername(newUserDto.getCredentials().getUsername());
			//uzer.setActive(true);
			userRepository.reactivate(uzer);//uzerJpaRepository.findByCredentialsUsername(newUserDto.getCredentials().getUsername()));
			//reactive all tweets, too?
			//tweetService.reactivateTweetsBy(uzer);
			tweetRepository.reactivateTweetsBy(uzer);
			//should I update the profile?
			return userMapper.toDto(uzer);
		}
		else if(validationService.isUsernameAvailable(newUserDto.getCredentials().getUsername()))
		{
			//make new user
			System.out.println("My new user dto is : " + newUserDto);
			Uzer uzer = newUserMapper.toUser(newUserDto);
			
			/*Try to handle this better*/
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
		else
			return null;//name taken, I guess
	}

	public UserDto deleteUser(String username, CredentialsDto credentialsDto) {
		if(!credentialsMatch(credentialsDto))
			return null;
		if(!this.userExistsAndIsActive(username))
			return null;
		Uzer user = uzerJpaRepository.findByCredentialsUsername(username);
		//tweetService.getRealTweets().forEach(tweet->tweet.setActive(false));//deactivate all their tweets
		tweetRepository.deactivateTweetsBy(user);
		return userMapper.toDto(userRepository.delete(user));
	}

	public UserDto updateUser(String username, NewUserDto newUserDto) {
		if(!userExistsAndIsActive(username))
			return null;
		if(!credentialsMatch(newUserDto.getCredentials()))
			return null;
		return userMapper.toDto(
				userRepository.updateUser(
						uzerJpaRepository.findByCredentialsUsername(username),
						profileMapper.toProfile(
								newUserDto.getProfile())));
		
	}

	public boolean followUser(String username, CredentialsDto credentialsDto) {
		
		if(!userExistsAndIsActive(username))
			return false;
		if(!credentialsMatch(credentialsDto))
			return false;
		Uzer userToBeFollowed = uzerJpaRepository.findByCredentialsUsername(username);
		Uzer userWhoWillFollow = uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername());
		if(userToBeFollowed.getFollowedBy().contains(userWhoWillFollow))
			return false;//can't follow again
		
		userRepository.followUser(userToBeFollowed, userWhoWillFollow);//uzerJpaRepository.findByCredentialsUsername(username), uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername()));
		return true;
	}

	public boolean unfollowUser(String username, CredentialsDto credentialsDto) {
		if(!userExistsAndIsActive(username))
			return false;
		if(!credentialsMatch(credentialsDto))
			return false;
		Uzer userToBeUnFollowed = uzerJpaRepository.findByCredentialsUsername(username);
		Uzer userWhoWillUnFollow = uzerJpaRepository.findByCredentialsUsername(credentialsDto.getUsername());
		if(!userToBeUnFollowed.getFollowedBy().contains(userWhoWillUnFollow))
			return false;//can't unfollow if not already following
		System.out.println("HERE HERE HERE");
		userRepository.unfollowUser(userToBeUnFollowed, userWhoWillUnFollow);
		return true;
	}

	public List<UserDto> getFollowers(String username) {
		if(!this.userExistsAndIsActive(username))
			return null;
		Uzer user = uzerJpaRepository.findByCredentialsUsername(username);
		return userMapper.toDtos(user.getFollowedBy());
	}

	public List<UserDto> getFollowing(String username) {
		if(!this.userExistsAndIsActive(username))
			return null;
		Uzer user = uzerJpaRepository.findByCredentialsUsername(username);
		return userMapper.toDtos(user.getFollowers());
	}

	public List<TweetDto> getFeed(String username) {
		if(!this.userExistsAndIsActive(username))
			return null;
		System.out.println("LENGTH: " + tweetRepository.getTweets().size());
		//look through all tweets, find ones authored by this guy or the guys followed by this guy
		List<TweetDto> list = tweetMapper.toDtos(tweetRepository.getTweets())
				.stream()
				.filter(tweetDto-> tweetDto.getAuthor().getUsername().equals(username) || getFollowing(username).contains(tweetDto.getAuthor()))
				.collect(Collectors.toList());
		list.sort(null);
		return list;
	}

	public List<TweetDto> getTweetsBy(String username) 
	{
		if(!this.userExistsAndIsActive(username))
			return null;
		List<TweetDto> list = tweetMapper.toDtos(tweetRepository.getTweets()).stream().filter(tweetDto->tweetDto.getAuthor().getUsername().equals(username)).collect(Collectors.toList());
		list.sort(null);
		return list;
	}
	
	public List<TweetDto> getMentions(String username) {
		if(!this.userExistsAndIsActive(username))
			return null;
		
		List<TweetDto> list = tweetMapper.toDtos(tweetRepository.getTweets()).stream().filter(tweetDto->Parser.mentionsName(username, tweetDto.getContent())).collect(Collectors.toList());
		list.sort(null);
		return list;
	}
	public boolean credentialsMatch(CredentialsDto credentials)
	{
		return credentialsRepository.credentialsMatch(credentials, userRepository.getAllUsers());
	}
}
