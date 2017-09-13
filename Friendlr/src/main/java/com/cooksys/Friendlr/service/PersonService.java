/**
 * 
 */
package com.cooksys.Friendlr.service;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.cooksys.Friendlr.entity.Person;

/**
 * @author Greg Hill
 */
@Service
public class PersonService {

	private Set<Person> people;
	private static long index = 0;
	
	public PersonService() {
		people = new TreeSet<Person>();
	}

	public Set<Person> getPeople() {
		return people;
	}

	public Person getPerson(long id) {
		for(Person person : people) {
			if(person.getId() == id) {
				return person;
			}
		}
		return null;
	}

	public Person createPerson(Person person) {
		person.setId(index++);
		people.add(person);
		
		return person;
	}

	public Person editPerson(long id, Person person) {
		Person oldPerson = getPerson(id);
		
		// Checks if a person with the given id exits
		if(oldPerson == null) {
			return null;			
		}
		
		// Checks that there is data for the first name
		if(person.getFirstName() != null) {
			oldPerson.setFirstName(person.getFirstName());
		}

		// Checks that there is data for the last name
		if(person.getLastName() != null) {
			oldPerson.setLastName(person.getLastName());
		}
		
		return oldPerson;
	}

	public boolean deletePerson(long id) {
		Person person = getPerson(id);
		
		// Checks if the person exits and then if the remove operation was successful
		if(person != null && people.remove(person)) {
			return true;
		} else {
			return false;
		}
	}
	
}
