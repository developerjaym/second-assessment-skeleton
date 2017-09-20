package com.cooksys.second.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.cooksys.second.entity.User;

public class UserRepository {

	private EntityManager entityManager;
	
	public UserRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public List<User> getAllUsers()
	{
		return entityManager.createQuery("FROM User", User.class).getResultList();
	}
}
