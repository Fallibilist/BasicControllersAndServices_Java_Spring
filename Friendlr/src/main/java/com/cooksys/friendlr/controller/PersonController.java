/**
 * 
 */
package com.cooksys.friendlr.controller;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.friendlr.dto.PersonDTO;
import com.cooksys.friendlr.service.PersonService;

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
	public Set<PersonDTO> getPeople() {
		return personService.getPeople();
	}

	@GetMapping("{id}")
	public PersonDTO getPerson(@PathVariable Long id, HttpServletResponse response) {
		PersonDTO person = personService.getPerson(id);
		
		if(person == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		return person;
	}
	
	@PostMapping
	public PersonDTO createPerson(@RequestBody PersonDTO person, HttpServletResponse response) {
		person = personService.createPerson(person);

		if(person == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		
		return person;
	}
	
	@PutMapping("{id}")
	public PersonDTO replacePerson(@PathVariable Long id, @RequestBody PersonDTO person, HttpServletResponse response) {
		person = personService.replacePerson(id, person);

		if(person == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return person;
	}
	
	@PatchMapping("{id}")
	public PersonDTO editPerson(@PathVariable Long id, @RequestBody PersonDTO person, HttpServletResponse response) {
		person = personService.editPerson(id, person);

		if(person == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return person;
	}
	
	@DeleteMapping("{id}")
	public PersonDTO deletePerson(@PathVariable Long id, HttpServletResponse response) {
		PersonDTO person = personService.getPerson(id);
		boolean deletionSuccessful = personService.deletePerson(id);

		if(!deletionSuccessful) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return person;
	}

	@GetMapping("{id}/friends")
	public Set<PersonDTO> getFriends(@PathVariable Long id) {
		return personService.getFriends(id);
	}

	@GetMapping("{id}/friends/{friendId}")
	public PersonDTO getFriends(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response) {
		PersonDTO friend = personService.getFriend(id, friendId);
		
		if(friend == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		return friend;
	}
	
	@PostMapping("{id}/friends/{friendId}")
	public PersonDTO addFriend(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response) {
		PersonDTO friend = personService.addFriend(id, friendId);

		if(friend == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		
		return friend;
	}
	
	@DeleteMapping("{id}/friends/{friendId}")
	public PersonDTO deleteFriend(@PathVariable Long id, @PathVariable Long friendId, HttpServletResponse response) {
		PersonDTO friend = personService.getFriend(id, friendId);
		boolean deletionSuccessful = personService.deleteFriend(id, friendId);

		if(!deletionSuccessful) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return friend;
	}
}
