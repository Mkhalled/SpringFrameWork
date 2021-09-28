package com.udemy.spring.rest.webservices.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.spring.rest.webservices.user.beans.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
