package com.cooksys.second.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.entity.Tweet;



@Mapper(componentModel="spring")
public interface TweetMapper {

	Tweet toTweet(TweetDto tweetDto);
	
	List<TweetDto> toDtos(List<Tweet> tweetDtos);
	
	List<Tweet> toUsers(List<TweetDto> tweets);

	TweetDto toDto(Tweet tweet);
}
