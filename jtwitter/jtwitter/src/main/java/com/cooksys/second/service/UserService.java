package com.cooksys.second.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.NewUserDto;
import com.cooksys.second.dto.UserDto;
import com.cooksys.second.entity.Uzer;
import com.cooksys.second.mapper.NewUserMapper;
import com.cooksys.second.mapper.UserMapper;
import com.cooksys.second.repository.UserRepository;
import com.cooksys.second.utility.TimeStamper;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private NewUserMapper newUserMapper;
	//mappers
	//other repositories
	
	
	public UserService(UserRepository userRepository, UserMapper userMapper, NewUserMapper newUserMapper)
	{
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.newUserMapper = newUserMapper;
	}
	
	public List<UserDto> getUsers() {
		// TODO Auto-generated method stub
		return userMapper.toDtos(userRepository.getAllUsers());
		//return null;
	}

	public UserDto getUser(Integer id)
	{
		return userMapper.toDto(userRepository.get(id));
	}
	
	public UserDto createUser(NewUserDto newUserDto) {
		
		System.out.println("My new user dto is : " + newUserDto);
		Uzer uzer = newUserMapper.toUser(newUserDto);
		
		/*Try to handle this in a no args constructor*/
		uzer.setActive(true);
		uzer.setJoined(TimeStamper.getTimestamp());
		//uzer.setFollowedBy(new HashSet<Uzer>());
		//uzer.setFollowers(new HashSet<Uzer>());
		
		uzer = userRepository.create(uzer);
		
		
		
		return userMapper.toDto(uzer);
	}

}
