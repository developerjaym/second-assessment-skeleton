package com.cooksys.second.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.cooksys.second.dto.CredentialsDto;
import com.cooksys.second.entity.Uzer;

@Repository
public class CredentialsRepository {
private EntityManager entityManager;
	
	public CredentialsRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	public boolean credentialsMatch(CredentialsDto credentials, List<Uzer> users) {
		//go through all Uzers, if one with these credentials is active, return true
		return users.stream().filter(uzer->uzer.getCredentials().getUsername().equals(credentials.getUsername()) && uzer.getCredentials().getPassword().equals(credentials.getPassword())).collect(Collectors.toList()).size()==1;
	}
}
