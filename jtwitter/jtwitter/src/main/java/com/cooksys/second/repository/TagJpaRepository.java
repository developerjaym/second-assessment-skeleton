package com.cooksys.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second.entity.Hashtag;


public interface TagJpaRepository extends JpaRepository<Hashtag, Integer>  {
	Hashtag findById(Integer id);
}
