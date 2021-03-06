package com.cooksys.second.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cooksys.second.dto.TweetDto;
import com.cooksys.second.dto.UserDto;
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
	
	@Transactional
	public Uzer create(Uzer uzer) {
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
		if(profile.getEmail() != null)
			uzer.getProfile().setEmail(profile.getEmail());
		if(profile.getFirstName() != null)
			uzer.getProfile().setEmail(profile.getFirstName());
		if(profile.getLastName() != null)
			uzer.getProfile().setLastName(profile.getLastName());
		if(profile.getPhone() != null)
			uzer.getProfile().setPhone(profile.getPhone());
		//uzer.setProfile(profile);
		entityManager.flush();//so the returned uzer is updated properly. Does this work?
		return uzer;
	}

	@Transactional
	public void followUser(Uzer followed, Uzer follower) {
		// TODO Auto-generated method stub
		followed.getFollowedBy().add(follower);
		follower.getFollowers().add(followed);//I should rename getFollowers()
	}

	@Transactional
	public void unfollowUser(Uzer followed, Uzer follower) {
		followed.getFollowedBy().remove(follower);
		follower.getFollowers().remove(followed);//I should rename getFollowers()
		
	}
	
	@Transactional
	public void reactivate(Uzer uzer) {
		uzer.setActive(true);
		
	}

	public Uzer getUser(Integer i) {
		return entityManager.find(Uzer.class, i);
	}
}
