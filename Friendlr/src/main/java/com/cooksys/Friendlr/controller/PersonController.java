/**
 * 
 */
package com.cooksys.Friendlr.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.Friendlr.entity.Person;
import com.cooksys.Friendlr.service.PersonService;

/**
 * @author Greg Hill
 */

@RestController
@RequestMapping("person")
public class PersonController {
	private PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@GetMapping
	public Set<Person> getPeople() {
		return personService.getPeople();
	}

	@GetMapping("{id}")
	public Person getPerson(@PathVariable long id, HttpServletResponse response) {
		Person person = personService.getPerson(id);
		
		if(person == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		return person;
	}
	
	@PostMapping
	public Person createPerson(@RequestBody Person person, HttpServletResponse response) {
		person = personService.createPerson(person);

		if(person == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		
		return person;
	}
	
	@PutMapping("{id}")
	public Person editPerson(@PathVariable long id, @RequestBody Person person, HttpServletResponse response) {
		person = personService.editPerson(id, person);

		if(person == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return person;
	}
	
	@DeleteMapping("{id}")
	public void deletePerson(@PathVariable long id, HttpServletResponse response) {
		boolean deletionSuccessful = personService.deletePerson(id);

		if(deletionSuccessful) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
