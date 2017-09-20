package com.cooksys.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second.entity.Uzer;

public interface UzerJpaRepository extends JpaRepository<Uzer, Integer> {

	Uzer findByCredentialsUsername(String username);
}
