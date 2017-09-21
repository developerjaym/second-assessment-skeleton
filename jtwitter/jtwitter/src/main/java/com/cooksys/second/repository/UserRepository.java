package com.cooksys.second.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.entity.Credentials;
import com.cooksys.second.entity.Profile;
import com.cooksys.second.entity.Uzer;

@Repository
public class UserRepository {

	private EntityManager entityManager;
	
	public UserRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public List<Uzer> getAllUsers()
	{
		return entityManager.createQuery("FROM Uzer", Uzer.class).getResultList();
	}

	public Uzer get(String username)
	{
		return entityManager.find(Uzer.class, username);
	}
	
	@Transactional
	public Uzer create(Uzer uzer) {
		// TODO Auto-generated method stub
		System.out.println("My new user is: " + uzer);
		entityManager.persist(uzer);
		return uzer;//should have an ID now, right?
		
	}

	@Transactional
	public Uzer delete(Uzer uzer) {
		// TODO Auto-generated method stub
		uzer.setActive(false);
		return uzer;
	}

	@Transactional
	public Uzer updateUser(Uzer uzer, Profile profile) {
		uzer.setProfile(profile);
		entityManager.flush();
		return uzer;
	}

	@Transactional
	public void followUser(Uzer followed, Uzer follower) {
		// TODO Auto-generated method stub
		followed.getFollowedBy().add(follower);
		follower.getFollowers().add(followed);//I should rename getFollowers()
	}

	public void unfollowUser(Uzer followed, Uzer follower) {
		followed.getFollowedBy().remove(follower);
		follower.getFollowers().remove(followed);//I should rename getFollowers()
		
	}
}
