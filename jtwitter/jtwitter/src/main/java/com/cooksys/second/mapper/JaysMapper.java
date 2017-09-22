package com.cooksys.second.mapper;

import java.util.ArrayList;
import java.util.List;

import com.cooksys.second.dto.ProfileDto;
import com.cooksys.second.dto.UserDto;
import com.cooksys.second.entity.Profile;
import com.cooksys.second.entity.Uzer;

public class JaysMapper {
	
	
    public static List<UserDto> toDtos(List<Uzer> uzers) {
        if ( uzers == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( uzers.size() );
        for ( Uzer uzer : uzers ) {
            list.add( toDto( uzer ) );
        }

        return list;
    }

    public static UserDto toDto(Uzer uzer) {
        if ( uzer == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( uzer.getId() );
        userDto.setProfile( profileToProfileDto( uzer.getProfile() ) );
        userDto.setJoined( uzer.getJoined() );
        userDto.setUsername(uzer.getCredentials().getUsername());
        
        return userDto;
    }

    protected static ProfileDto profileToProfileDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setFirstName( profile.getFirstName() );
        profileDto.setLastName( profile.getLastName() );
        profileDto.setEmail( profile.getEmail() );
        profileDto.setPhone( profile.getPhone() );

        return profileDto;
    }
}
