package com.cooksys.second.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
		
		return entityManager.createQuery("FROM Tweet", Tweet.class).getResultList().stream().filter(tweet->tweet.getActive()).collect(Collectors.toList());
		
		
	}

	@Transactional
	public Tweet createTweet(Tweet tweet) {
		
		//maybe start a context, too
		
		entityManager.persist(tweet);
		return tweet;
	}

	@Transactional
	public void deleteTweet(Tweet tweet) {
		tweet.setActive(false);
	}
	
}
