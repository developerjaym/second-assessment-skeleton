package com.cooksys.second.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.entity.Tweet;
import com.cooksys.second.entity.Uzer;

@Repository
public class TweetRepository {

	private EntityManager entityManager;
	
	public TweetRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	public List<Tweet> getTweets() {
		
		return entityManager.createQuery("FROM Tweet", Tweet.class).getResultList().stream().filter(tweet->tweet.isActive()).collect(Collectors.toList());
		
		
	}
	
}
