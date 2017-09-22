package com.cooksys.second.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.second.repository.TagRepository;
import com.cooksys.second.repository.UserRepository;
import com.cooksys.second.repository.UzerJpaRepository;

@Service
public class ValidationService {

	//private UserService userService;
	//private TagService tagService;
	
	private UserRepository userRepository;
	private TagRepository tagRepository;
	
	public ValidationService(UserRepository userRepository, TagRepository tagRepository)//(UserService userService, TagService tagService)
	{
		//this.userService = userService;
		//this.tagService = tagService;
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
	}
	
	public boolean isHashtagExistent(String label) {
		
		return tagRepository.getAllTags().stream().filter(hashtag->hashtag.getLabel().equals(label)).collect(Collectors.toList()).size()>0;
	}

	public boolean isUsernameExistent(String username) {
		return userRepository.getAllUsers().stream().filter(uzer->uzer.getCredentials().getUsername().equals(username) && uzer.isActive()).collect(Collectors.toList()).size()==1;
	}

	public boolean isUsernameAvailable(String username) {
		//return userService.getEvenDeletedUsers().stream().filter(userDto->userDto.getUsername().equals(username)).collect(Collectors.toList()).size()==0;
		return userRepository.getAllUsers().stream().filter(uzer->uzer.getCredentials().getUsername().equals(username)).collect(Collectors.toList()).size()==0;
	}

}
