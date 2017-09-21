package com.cooksys.second.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.entity.Hashtag;


@Mapper(componentModel="spring")
public interface TagMapper {

	
	Hashtag toTweet(HashtagDto hashtagDtos);
	
	List<HashtagDto> toDtos(List<Hashtag> hashtagDtos);
	
	List<Hashtag> toHashtags(List<HashtagDto> hashtags);

	HashtagDto toDto(Hashtag hashtag);
}
