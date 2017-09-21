package com.cooksys.second.service;

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

}
