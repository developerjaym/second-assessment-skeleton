package com.cooksys.second.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.entity.Tweet;



@Mapper(componentModel="spring")
public interface TweetMapper {

	Tweet toTweet(TweetDto tweetDto);
	
	List<TweetDto> toDtos(List<Tweet> uzers);
	
	List<Tweet> toUsers(List<TweetDto> users);

	TweetDto toDto(Tweet uzer);
}
