package com.udemy.spring.rest.webservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	@GetMapping("v1/person")
	public PersonV1 retrievePersonV1() {
		return new PersonV1("Meneouali Omar");

	}

	@GetMapping("v2/person")
	public PersonV2 retrievePersonV2() {
		return new PersonV2(new Name("Meneouali", "Omar"));

	}

	@GetMapping(value = "/person/param", params = "version=1")
	public PersonV1 retrievePersonV1Param() {
		return new PersonV1("Meneouali Omar");

	}

	@GetMapping(value = "/person/param", params = "version=2")
	public PersonV2 retrievePersonV2Param() {
		return new PersonV2(new Name("Meneouali", "Omar"));

	}

	@GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
	public PersonV1 retrievePersonV1Header() {
		return new PersonV1("Meneouali Omar");

	}

	@GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
	public PersonV2 retrievePersonV2Heders() {
		return new PersonV2(new Name("Meneouali", "Omar"));

	}

	@GetMapping(value = "/person/produces", produces = "application/com.udemy.spring.app-v1+json")
	public PersonV2 retrievePersonV1Produces() {
		return new PersonV2(new Name("Meneouali", "Omar"));

	}

	@GetMapping(value = "/person/produces", produces = "application/com.udemy.spring.app-v2+json")
	public PersonV2 retrievePersonV2Produces() {
		return new PersonV2(new Name("Meneouali", "Omar"));

	}
}
