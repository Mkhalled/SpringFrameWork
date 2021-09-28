MicroServices with Spring Boot and Spring Cloud 
====================
>> # Part 1 - RESTful Web Services :


>>##  RESTful Services  

>>> - ** Step 01 ** - Initializing a RESTful Services Project with Spring Boot
>>> - ** Step 02 ** - Creating a Hello World Service
>>> - ** Step 03 ** - Enhancing the Hello World Service to return a Bean
>>> - ** Step 04 ** - Enhancing the Hello World Service with a Path Variable
>>> - ** Step 05 ** - Creating User Bean and User Service
>>> - ** Step 06 ** - Implementing GET Methods for User Resource
>>> - ** Step 07 ** - Implementing POST Method to create User Resource
>>> - ** Step 12 ** - Implementing DELETE Method to delete a User Resource

>>>### Annotation 

>>>>
```
- @Component :
- @RestControlller :
- @Autoward : 
- @RequestMapping (method=requestMethod.GET/POST/DELETE/.., Path="/path") 
or @GetMapping (path="/path") / @PostMApping(path) / DeleteMapping(path)
- @PathVariable (GetById)
- @RequestBody									
```

>>##  Status   

>>> - ** Step 08 ** - Enhancing POST Method to return correct HTTP Status Code and Location URI

>>>>
```
		@PostMapping("/jpa/users")
		public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
			User createdUser = userRepository.save(user);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(createdUser.getId())
					.toUri();
			return ResponseEntity.created(location).build();
	}
```

>>## Exceptions 

>>> - ** Step 09 ** - Implementing Exception Handling - 404 Resource Not Found

>>>>
```
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
```



>>> - ** Step 10 ** - Implementing Generic Exception Handling for all Resources

>>>> 1. create class Exception response 
>>>>
```
public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String detail;
	// getters & setters
	}
```


>>>> 2. create class CustomizedResponseEntityExceptionHandler 
>>>>
```
@RestControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Faild",
				ex.getBindingResult().toString());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
```



>>## Validations 

>>> - ** Step 13 ** - Implementing Validations for RESTful Services

>>## HATEOAS

>>> - ** Step 14 ** - Implementing HATEOAS for RESTful Services:
 
>>>> HATEOAS is a concept of application architecture. It defines the way in which application clients interact with the server, by navigating hypermedia links they find inside resource models returned by the server.

>>>> - Add dependency
>>>>
		<dependency>
		<groupId>org.springframework.data</groupId>
		<artifactId>spring-data-rest-hal-explorer</artifactId>
		</dependency>

>>>>		
		@GetMapping("/jpa/users/{id}")
		public EntityModel<Optional<User>> retrieveUserById(@PathVariable Integer id) 
		{
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) throw new UserNotFoundException("id - " + id);
		//
		EntityModel<Optional<User>> modelUser = EntityModel.of(user);
		WebMvcLinkBuilder linkUsers = linkTo(methodOn(this.getClass()).retrieveAllUser());
		modelUser.add(linkUsers.withRel("all-users"));
		return modelUser;
	    } 



>>## Internationalization

>>> - ** Step 15 ** - Internationalization for RESTful Services


>>>> - Add annotation in method

>>>>
		@GetMapping(path = "/hello-world-internationalized")
		public String helloWordInternationalized(@RequestHeader(
		 name = "Accept-Language", 
		 required = false) Locale locale) {}
		
>>>> - or
>>>>
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWordInternationalized() 
	{
		return messageSource.getMessage("good.morning.message=", null, "Default Message",
				LocaleContextHolder.getLocale());
	}
	

>>## Content Negotiation

>>> - ** Step 16 ** - Content Negotiation - Implementing Support for XML

>>>> - Add dependency

>>>>
		<dependency>
		 <groupId>com.fasterxml.jackson.dataformat</groupId>
		<artifactId>jackson-dataformat-xml</artifactId>
		</dependency>
		
>>>> - in the header added Accept  application/Xml		

>>## Swagger / OpenAPI 

>>> - ** Step 17 ** - Configuring Auto Generation of Swagger Documentation and Introduction to Swagger Documentation Format

>>>> - Add dependency
>>>>
		<dependency>
		<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-ui</artifactId>
		<version>1.5.10</version>
		</dependency>

>>>> - Url to access to swagger : [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/).

 
>>## Actuator 


>>> - ** Step 18 ** - Monitoring APIs with Spring Boot Actuator

>>>> - Add dependency

>>>>
		<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>	
		
>>>> -	Activate all the features of Actuator.
>>>> 
    management.endpoints.web.exposure.include=*
 
>>>> - Url to access to Actuator : [http://localhost:8080/Actuator](http://localhost:8080/Actuator).


>>## Filtering

>>> - ** Step 19 ** - Implementing Static Filtering for RESTful Service

>>>> - Add annotation 

>>>> 
```
		@JsonIgnore 
		@JsonIgnoreProperties(value={"field1","field2"…})
```
>>> - ** Step 20 ** - Implementing Dynamic Filtering for RESTful Service

>>>>
```
	@GetMapping("/filtring")
	public MappingJacksonValue retriveSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		return mapping;
	}
```
>>## Versioning

>>> - ** Step 21 ** - Versioning RESTful Services - Basic Approach with URIs
>>> - ** Step 22 ** - Versioning RESTful Services - Header and Content Negotiation Approaches

>>## Security

>>> - ** Step 23 ** - Implementing Basic Authentication with Spring Security
>>>> - Add dependency

>>>>
	<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
		
>>>> -	Add the following properties to change the basic login and password
>>>> 
```
   	management.security.enabled=false
	spring.security.user.name=username
	spring.security.user.password=password
```
>> ## JPA 

>>> - ** Step 24 - Creating User Entity and some test data
>>> - ** Step 25 - Updating GET methods on User Resource to use JPA
>>> - ** Step 26 - Updating POST and DELETE methods on User Resource to use JPA
>>> - ** Step 27 - Creating Post Entity and Many to One Relationship with User Entity
>>> - ** Step 28 - Implementing a GET service to retrieve all Posts of a User
>>> - ** Step 29 - Implementing a POST service to create a Post for a User
