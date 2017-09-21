package com.cooksys.second.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.entity.Context;
import com.cooksys.second.entity.Hashtag;
import com.cooksys.second.entity.Tweet;
import com.cooksys.second.entity.Uzer;
import com.cooksys.second.utility.TimeStamper;

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
		entityManager.flush();//so I get an id. Does this work?
		
		Context context = new Context();
		context.setAfter(new HashSet<Tweet>());
		context.setBefore(new HashSet<Tweet>());
		context.setTweetId(tweet.getId());
		entityManager.persist(context);
		
		return tweet;
	}

	@Transactional
	public void deleteTweet(Tweet tweet) {
		tweet.setActive(false);
	}

	public List<Hashtag> getAllHashtags() {
		
		return entityManager.createQuery("FROM Hashtag", Hashtag.class).getResultList();
		
	}
	
	@Transactional
	public void createHashtags(List<Hashtag> hashtags) {
		//go through each hashtag
		//if it is already in the db, udpate the lastUsed column
		//else, add it to the db and set the firstUsed and lastUsed
		List<Hashtag> existingHashtags = getAllHashtags();
		hashtags.forEach((Hashtag hashtag)->{
			if(existingHashtags.contains(hashtag))
			{
				hashtag.setLastUsed(TimeStamper.getTimestamp());
			}
			else
			{
				hashtag.setFirstUsed(TimeStamper.getTimestamp());
				hashtag.setLastUsed(hashtag.getFirstUsed());
				entityManager.persist(hashtag);
			}
			
		});
		
	}
	
}
