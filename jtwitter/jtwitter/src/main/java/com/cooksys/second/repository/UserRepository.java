package com.cooksys.second.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

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

	public Uzer get(Integer id)
	{
		return entityManager.find(Uzer.class, id);
	}
	
	@Transactional
	public Uzer create(Uzer uzer) {
		// TODO Auto-generated method stub
		System.out.println("My new user is: " + uzer);
		entityManager.persist(uzer);
		return uzer;//should have an ID now, right?
		
	}
}
