package com.cooksys.second.mapper;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.NewUserDto;
import com.cooksys.second.entity.Uzer;

@Mapper(componentModel="spring")
public interface NewUserMapper {

	
	Uzer toUser(NewUserDto newUserDto);
	
}
