package com.cooksys.second.repository;

import java.util.ArrayList;
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
	
	public List<Context> GetContexts() 
	{
		return entityManager.createQuery("FROM Context", Context.class).getResultList();
	}
	
	public Context get(Integer contextId) {

		return entityManager.find(Context.class, contextId);

	}
	
	@Transactional
	public Tweet createTweet(Tweet tweet) {
		
		//maybe start a context, too
		
		entityManager.persist(tweet);
		entityManager.flush();//so I get an id. Does this work?
		
		Context context = new Context();
		context.setAfter(new Integer[1]);
		context.setBefore(new Integer[1]);
		context.setTarget(tweet.getId());//.setTweetId(tweet.getId());
		entityManager.persist(context);
		
		tweet.setContextId(context.getId());
		
		return tweet;
	}

	@Transactional
	public Tweet createReply(Tweet reply, Tweet original) {
		entityManager.persist(reply);
		entityManager.flush();//so I get an id. Does this work?
		
		Context originalContext = this.get(original.getContextId());
		Integer[] after = new Integer[originalContext.getAfter().length+1];
		for(int i = 0; i < after.length-1; i++)
		{
			after[i] = originalContext.getAfter()[i];
		}
		after[after.length-1] = reply.getId();
		originalContext.setAfter(after);
		
		Context context = new Context();//make a new context //if you have free time, do this more intelligently
		context.setAfter(new Integer[0]);
		context.setBefore(new Integer[1]);
		//context.getBefore().add(original.getId());
		context.getBefore()[0] = original.getId();
		context.setTarget(reply.getId());//.setTweetId(tweet.getId());
		entityManager.persist(context);
		
		reply.setContextId(context.getId());
		
		return reply;
	}
	
	@Transactional
	public Tweet createRepost(Tweet tweet) {
		
		entityManager.persist(tweet);
		entityManager.flush();//so I get an id. Does this work?
		
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
	public void createHashtags(List<String> hashtags) {
		//go through each hashtag
		//if it is already in the db, udpate the lastUsed column
		//else, add it to the db and set the firstUsed and lastUsed
		List<Hashtag> existingHashtags = getAllHashtags();
		existingHashtags.forEach((hashtag)->{
			if(hashtags.contains(hashtag.getLabel()))
			{
				hashtag.setLastUsed(TimeStamper.getTimestamp());
				hashtags.remove(hashtag.getLabel());
			}
			
		});
		
		hashtags.forEach(string->{
			Hashtag tag = new Hashtag();
			tag.setLabel(string);
			tag.setFirstUsed(TimeStamper.getTimestamp());
			tag.setLastUsed(tag.getFirstUsed());
			entityManager.persist(tag);
			entityManager.flush();//to avoid possible id problems
		});
		
	}

	public void reactivateTweetsBy(Uzer author) {
		List<Tweet> list = entityManager.createQuery("FROM Tweet", Tweet.class).getResultList().stream().filter(tweet->tweet.getAuthor().equals(author)).collect(Collectors.toList());
		list.forEach(tweet->tweet.setActive(true));
		
	}

	public void deactivateTweetsBy(Uzer author) {
		List<Tweet> list = entityManager.createQuery("FROM Tweet", Tweet.class).getResultList().stream().filter(tweet->tweet.getAuthor().equals(author)).collect(Collectors.toList());
		list.forEach(tweet->tweet.setActive(false));
		
	}

	@Transactional
	public void likeTweet(Tweet tweet, Integer id) {
		if(tweet.getLikedBy() == null)
			tweet.setLikedBy(new Integer[0]);
		Integer[] likers = new Integer[tweet.getLikedBy().length+1];
		for(int i = 0; i < likers.length-1; i++)
		{
			likers[i] = tweet.getLikedBy()[i];
		}
		likers[likers.length-1] = id;
		tweet.setLikedBy(likers);
		
	}

	
	
}
