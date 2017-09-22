package com.cooksys.second.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.mapper.TagMapper;
import com.cooksys.second.mapper.TweetMapper;
import com.cooksys.second.repository.TagJpaRepository;
import com.cooksys.second.repository.TagRepository;
import com.cooksys.second.repository.TweetRepository;
import com.cooksys.second.utility.Parser;

@Service
public class TagService {

	private TagRepository tagRepository;
	private TagJpaRepository tagJpaRepository;
	private TagMapper tagMapper;
	//private TweetService tweetService;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	public TagService(TweetMapper tweetMapper,/*TweetService tweetService*/TweetRepository tweetRepository, TagMapper tagMapper, TagRepository tagRepository, TagJpaRepository tagJpaRepository)
	{
		this.tagJpaRepository = tagJpaRepository;
		this.tagRepository = tagRepository;
		this.tagMapper = tagMapper;
		//this.tweetService = tweetService;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}
	
	
	public List<HashtagDto> getAllTags() {
		
		return tagMapper.toDtos(tagRepository.getAllTags());
	}


	public List<TweetDto> getTweetsTaggedSo(String label) {
		List<TweetDto> allActiveTweets = tweetMapper.toDtos(tweetRepository.getTweets());//tweetService.getTweets();
		List<TweetDto> allWithHashtag = allActiveTweets.stream().filter(tweetdto->Parser.containsTag(label, tweetdto.getContent())).collect(Collectors.toList());
		allWithHashtag.sort(null);
		return allWithHashtag;
	}

}
