package com.udemy.spring.rest.webservices.user.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import com.udemy.spring.rest.webservices.user.beans.Post;
import com.udemy.spring.rest.webservices.user.beans.User;
import com.udemy.spring.rest.webservices.user.dao.PostRepository;
import com.udemy.spring.rest.webservices.user.dao.UserRepository;

@RestController
public class UserJpaController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUser() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<Optional<User>> retrieveUserById(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException("id - " + id);

		EntityModel<Optional<User>> modelUser = EntityModel.of(user);

		WebMvcLinkBuilder linkUsers = linkTo(methodOn(this.getClass()).retrieveAllUser());
		modelUser.add(linkUsers.withRel("all-users"));
		return modelUser;
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User createdUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUserById(@PathVariable Integer id) {
		userRepository.deleteById(id);

	}

	// API FOR POST ENTITY

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostByIdUser(@PathVariable Integer id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("id - " + id);
		}
		return optionalUser.get().getPosts();

	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable Integer id, @RequestBody Post post) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("id - " + id);
		}

		post.setUser(optionalUser.get());

		Post createdPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(createdPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

}
