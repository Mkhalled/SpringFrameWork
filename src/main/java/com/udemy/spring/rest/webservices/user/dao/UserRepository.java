package com.udemy.spring.rest.webservices.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.spring.rest.webservices.user.beans.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
