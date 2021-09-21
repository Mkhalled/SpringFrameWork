package com.udemy.spring.rest.webservices.helloWorld.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.spring.rest.webservices.helloWorld.beans.HelloWorldBean;

@RestController
public class HelloWorldController {

	@Autowired
	MessageSource messageSource;

	// @RequestMapping(method = RequestMethod.GET,path = "/hello-world")
	@GetMapping(path = "/hello-world")
	public String helloWord() {

		return "Hello Word ";
	}

	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWordBean() {

		return new HelloWorldBean("Hello Word Bean");
	}

	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWordPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello Word Bean , %s ", name));
	}

}
