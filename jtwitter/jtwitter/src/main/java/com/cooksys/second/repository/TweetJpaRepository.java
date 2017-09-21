package com.cooksys.second.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.second.entity.Tweet;


public interface TweetJpaRepository extends JpaRepository<Tweet, Integer> {

	Tweet findById(Integer id);
}
