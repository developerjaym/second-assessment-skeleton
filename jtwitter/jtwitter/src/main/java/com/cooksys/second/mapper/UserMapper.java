package com.cooksys.second.mapper;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.UserDto;
import com.cooksys.second.entity.User;


@Mapper(componentModel="spring")
public interface UserMapper {
	UserDto toPersonDto(User user);
	
	User toPerson(UserDto userDto);
}