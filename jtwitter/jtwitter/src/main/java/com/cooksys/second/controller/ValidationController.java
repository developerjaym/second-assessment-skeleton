package com.cooksys.second.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.second.service.ValidationService;

@RestController
@RequestMapping("validate")
public class ValidationController {
	
	private ValidationService validationService;
	
	public ValidationController(ValidationService validationService)
	{
		this.validationService = validationService;
	}
	@GetMapping("tag/exists/{label}")
	public boolean isHashtagExistent(@PathVariable String label)
	{
		//returns true if the given hashtag exists
		return false;
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean isUsernameExistent(@PathVariable String username)
	{
		//returns true if the username exists
			//(the user is active)
		
		return false;
	}
	
	
	@GetMapping("username/available/@{username}")
	public boolean isUsernameAvailable(@PathVariable String username)
	{
		//return true if the username is available
			//no active or inactive user with that name
			// a new user can choose this name
		
		return false;
	}

}
