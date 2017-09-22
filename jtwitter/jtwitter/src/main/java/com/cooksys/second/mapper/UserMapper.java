package com.cooksys.second.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.UserDto;
import com.cooksys.second.entity.Uzer;


@Mapper(componentModel="spring")
public interface UserMapper {
	//UserDto toUserDto(User user);
	
	
	List<UserDto> toDtos(List<Uzer> uzers);
	

	UserDto toDto(Uzer uzer);
}