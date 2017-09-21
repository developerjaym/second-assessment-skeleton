package com.cooksys.second.controller;

import javax.servlet.http.HttpServletResponse;

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
	public boolean isHashtagExistent(@PathVariable String label, HttpServletResponse response)
	{
		response.setStatus(200);
		return validationService.isHashtagExistent(label);
	}
	
	@GetMapping("username/exists/@{username}")
	public boolean isUsernameExistent(@PathVariable String username, HttpServletResponse response)
	{
		response.setStatus(200);
		return validationService.isUsernameExistent(username);
	}
	
	
	@GetMapping("username/available/@{username}")
	public boolean isUsernameAvailable(@PathVariable String username, HttpServletResponse response)
	{
		response.setStatus(200);
		return validationService.isUsernameAvailable(username);
	}

}
