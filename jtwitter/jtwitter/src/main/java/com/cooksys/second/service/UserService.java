package com.cooksys.second.service;

import com.cooksys.second.dto.UserDto;
import com.cooksys.second.repository.UserRepository;

public class UserService {

	private UserRepository userRepository;
	//mappers
	//other repositories
	
	
	public UserService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	public UserDto[] getUsers() {
		// TODO Auto-generated method stub
		//return userMapper.toDtos(userRepository.getAllUsers());
		return null;
	}

}
