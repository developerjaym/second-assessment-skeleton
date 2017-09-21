package com.cooksys.second.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.mapper.TagMapper;
import com.cooksys.second.repository.TagJpaRepository;
import com.cooksys.second.repository.TagRepository;

@Service
public class TagService {

	private TagRepository tagRepository;
	private TagJpaRepository tagJpaRepository;
	private TagMapper tagMapper;
	
	public TagService(TagMapper tagMapper, TagRepository tagRepository, TagJpaRepository tagJpaRepository)
	{
		this.tagJpaRepository = tagJpaRepository;
		this.tagRepository = tagRepository;
		this.tagMapper = tagMapper;
	}
	
	
	public List<HashtagDto> getAllTags() {
		
		return tagMapper.toDtos(tagRepository.getAllTags());
	}

}
