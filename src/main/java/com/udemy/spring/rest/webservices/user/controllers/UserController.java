package com.udemy.spring.rest.webservices.user.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.udemy.spring.rest.webservices.user.Exceptions.UserNotFoundException;
import com.udemy.spring.rest.webservices.user.beans.User;
import com.udemy.spring.rest.webservices.user.dao.userDaoService;

@RestController
public class UserController {

	@Autowired
	userDaoService services;

	@GetMapping("/users")
	public List<User> retrieveAllUser() {
		return services.findAll();
	}

	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUserById(@PathVariable Integer id) {
		User user = services.findOne(id);

		if (user == null)
			throw new UserNotFoundException("id - " + id);

		EntityModel<User> modelUser = EntityModel.of(user);

		WebMvcLinkBuilder linkUsers = linkTo(methodOn(this.getClass()).retrieveAllUser());
		modelUser.add(linkUsers.withRel("all-users"));
		return modelUser;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User createdUser = services.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable Integer id) {
		User user = services.deleteUserById(id);

		if (user == null)
			throw new UserNotFoundException("id - " + id);

	}

}
