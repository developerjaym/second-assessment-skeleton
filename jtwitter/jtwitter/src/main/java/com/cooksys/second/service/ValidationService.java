package com.cooksys.second.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

	private UserService userService;
	private TagService tagService;
	
	public ValidationService(UserService userService, TagService tagService)
	{
		this.userService = userService;
		this.tagService = tagService;
	}
	
	public boolean isHashtagExistent(String label) {
		
		return tagService.getAllTags().contains(label);
	}

	public boolean isUsernameExistent(String username) {
		
		//may need to change later
		if(userService.getUser(username) == null)
			return false;
		else
			return true;
		
	}

	public boolean isUsernameAvailable(String username) {
		//return true if the username is available
		//no active or inactive user with that name
		// a new user can choose this name
		
		return userService.getEvenDeletedUsers().stream().filter(userDto->userDto.getUsername().equals(username)).collect(Collectors.toList()).size()==0;
		
		
	}

}
