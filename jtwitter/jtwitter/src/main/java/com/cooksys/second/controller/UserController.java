package com.cooksys.second.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

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
	public List<UserDto> getUsers(HttpServletResponse response)
	{
		return userService.getUsers();
	}
	
	@PostMapping 
	public UserDto createUser(@RequestBody NewUserDto newUserDto, HttpServletResponse response)
	{
		UserDto userDto = userService.createUser(newUserDto);
		
		if(userDto == null)
			response.setStatus(406);//not acceptable
		
		return userDto;
	}
	
	@GetMapping("@{username}")
	public UserDto getUser(@PathVariable String username, HttpServletResponse response)
	{
		UserDto userDto = userService.getUser(username);
		if(userDto == null)
			response.setStatus(404);
		return userDto;
	}
	@PatchMapping("@{username}")
	public UserDto updateProfile(@PathVariable String username, @RequestBody NewUserDto newUserDto, HttpServletResponse response)
	{
		UserDto userDto = userService.updateUser(username, newUserDto);
		if(userDto == null)
			response.setStatus(404);
		
		return userDto;
	}
	@DeleteMapping("@{username}")
	public UserDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response)
	{
		UserDto userDto = userService.deleteUser(username, credentialsDto);
		
		if(userDto == null)
			response.setStatus(406);
		
		return userDto;
	}
	@PostMapping("@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response)
	{
		if(!userService.followUser(username, credentialsDto))
			response.setStatus(406);
	}
	@PostMapping("@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto, HttpServletResponse response)
	{
		if(!userService.unfollowUser(username, credentialsDto))
			response.setStatus(406);
	}
	@GetMapping("@{username}/feed")
	public List<TweetDto> getFeed(@PathVariable String username, HttpServletResponse response)
	{
		List<TweetDto> list = userService.getFeed(username);
		if(list == null)
			response.setStatus(404);
		return list;
	}
	
	@GetMapping("@{username}/tweets")
	public List<TweetDto> getTweets(@PathVariable String username, HttpServletResponse response)
	{
		List<TweetDto> list = userService.getTweetsBy(username);
		if(list == null)
			response.setStatus(404);
		return list;
	}
	@GetMapping("@{username}/mentions")
	public List<TweetDto> getMentions(@PathVariable String username, HttpServletResponse response)
	{
		List<TweetDto> list = userService.getMentions(username);
		if(list == null)
			response.setStatus(404);
		return list;
		
	}
	@GetMapping("@{username}/followers")
	public List<UserDto> getFollowers(@PathVariable String username, HttpServletResponse response)
	{
		List<UserDto> list = userService.getFollowers(username);
		if(list == null)
			response.setStatus(404);
		return list;
		
	}
	@GetMapping("@{username}/following")
	public List<UserDto> getFollowing(@PathVariable String username, HttpServletResponse response)
	{
		List<UserDto> list = userService.getFollowing(username);
		if(list == null)
			response.setStatus(404);
		return list;
	}
	

}
