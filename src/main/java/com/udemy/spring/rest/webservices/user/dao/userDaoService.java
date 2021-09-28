package com.udemy.spring.rest.webservices.user.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.udemy.spring.rest.webservices.user.beans.User;

@Component
public class userDaoService {
	private static int userCount = 3;
	private static List<User> users = new ArrayList<User>();

	static {

		users.add(new User(1, "khalled", new Date()));
		users.add(new User(2, "Omar", new Date()));
		users.add(new User(3, "Ali", new Date()));

	}

	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;

	}

	public User findOne(Integer id) {
		for (User u : users) {
			if (u.getId() == id) {
				return u;
			}
		}
		return null;
	}

	public User deleteUserById(Integer id) {
		for (User u : users) {
			if (u.getId() == id) {
				users.remove(u);
				return u;
			}

		}
		return null;
	}

}
