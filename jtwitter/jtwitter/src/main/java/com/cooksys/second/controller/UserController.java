package com.cooksys.second.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.dto.NewUserDto;
import com.cooksys.second.dto.ProfileDto;
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
	public UserDto[] getUsers()
	{
		//return all non-deleted users as an array
		return userService.getUsers();
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
		return null;
	}
	
	@GetMapping("{id}")
	public UserDto getUser(@PathVariable Integer id)
	{
		//if no such user, send error
		//if user is deleted, send error
		
		//return a User
		return null;
	}
}
