package com.cooksys.second.mapper;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.ProfileDto;
import com.cooksys.second.entity.Profile;


@Mapper(componentModel="spring")
public interface ProfileMapper {
	ProfileDto toProfileDto(Profile profile);
	
	Profile toProfile(ProfileDto profileDto);
}
