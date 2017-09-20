package com.cooksys.second.mapper;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.entity.Credentials;


@Mapper(componentModel="spring")
public interface CredentialsMapper {
	CredentialsDto toCredentailsDto(Credentials credentials);
	
	Credentials toCredentials(CredentialsDto credentialsDto);
}
