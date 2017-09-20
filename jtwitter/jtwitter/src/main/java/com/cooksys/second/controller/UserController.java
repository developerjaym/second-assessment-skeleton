package com.cooksys.second.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.dto.NewUserDto;
import com.cooksys.second.dto.ProfileDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.dto.UserDto;
import com.cooksys.second.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService)
	{
		this.userService = userService;
	}
	
	@GetMapping()
	public List<UserDto> getUsers()
	{
		//return all non-deleted users as an array
		return userService.getUsers();//.toArray();
	}
	
	@PostMapping 
	public  UserDto createUser(@RequestBody NewUserDto newUserDto)
	{
		//This method will be sent a Request like this:
			//credentials
			//profile
		//I don't have any idea how this will work
		
		//if any required fields are missing
		//if username is already taken
		//send error
		
		//reactivate if credentials match previously-deleted user
		
		UserDto userDto = userService.createUser(newUserDto);
		
		return userDto;
	}
	
	@GetMapping("@{username}")
	public UserDto getUser(@PathVariable String username)
	{
		//if no such user, send error
		//if user is deleted, send error
		
		//return a User
		return userService.getUser(username);
	}
	@PatchMapping("@{username}")
	public UserDto updateProfile(@PathVariable String username, @RequestBody NewUserDto newUserDto)
	{
		//updates the profile with the given username
		
		//if no such user, if deleted user, or if credentials don't match, send error
		
		//if successful return user with updated data
		
		userService.updateUser(username, newUserDto);
		
		return null;
	}
	@DeleteMapping("@{username}")
	public UserDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto)
	{
		//deactivates user with given username
		//if no such user or credentials don't match, send error
		//if successful, return user with the user data prior to deletion
		
		//don't really drop any records from the database
		
		userService.deleteUser(username, credentialsDto);
		
		return null;
	}
	@PostMapping("@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto)
	{
		//subscribes the user whose credentials are provided by the request body
			//to the user whose username is given in the url
		//if relationship already exists, no such followable user exists, or credentials do not match anyone,
			//send error
		//if successful, no data is sent
		
		userService.followUser(username, credentialsDto);
	}
	@PostMapping("@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto)
	{
		//unsubscribes the user whose credentials are given from the user whose usename is given
		//if no preexisting relationship, no such followable user exists, or credentials amtch no one,
			//send error
		//if successful, no data is sent
		
		userService.unfollowUser(username, credentialsDto);
	}
	@GetMapping("@{username}/tweets")
	public TweetDto[] getTweets(@PathVariable String username)
	{//consider using a different return type, consider a list, stack, or something like that
		
		//retrieves all (non-deleted) tweets authored by the user with the given username
		//this includes all three kinds of tweets
		//tweets should appear in reverse-chronological order
		
		//if no active user with that username
			//send error
		//if successful
			//repond with ['Tweet']
		
		return null;
	}
	@GetMapping("@{username}/mentions")
	public TweetDto[] getMentions(@PathVariable String username)
	{//consider using a different return type, consider a list, stack, or something like that
		
		//retrieves all non-deleted tweets in which the user with the username is mentioned
		//tweets should appear in reverse-chronological order
		//@username in a tweet's content means the user is mentioned
		//only tweets with content can mention someone
		
		//if no active user with that username exists,
			//send error
		//if successful, return ['Tweet']
		
		
		return null;
		
	}
	@GetMapping("@{username}/followers")
	public List<UserDto> getFollowers(@PathVariable String username)
	{
		//retrieves the followers of hte user with the given username
		//only active users should be included in the response
		
		//if no active user with the given username,
			//send error
		
		//if successful, return ['User']
		
		return userService.getFollowers(username);
		
		//return null;
		
	}
	@GetMapping("@{username}/following")
	public List<UserDto> getFollowing(@PathVariable String username)
	{
		//retrieves the users followed by the user with the given username
		//only active users should be included in response
		
		//if no user exists with that name, 
			//send error
		//if successful, return ['User']
		
		
		return userService.getFollowing(username);
		//return null;
	}
	

}
