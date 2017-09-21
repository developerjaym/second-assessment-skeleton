package com.cooksys.second.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.cooksys.second.dto.HashtagDto;
import com.cooksys.second.entity.Hashtag;
import com.cooksys.second.entity.Tweet;
import com.cooksys.second.entity.Uzer;

@Repository
public class TagRepository {

	private EntityManager entityManager;
	
	public TagRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	public List<Hashtag> getAllTags() {
		
		return entityManager.createQuery("FROM Hashtag", Hashtag.class).getResultList();
		
	}
	
}
