/**
 * 
 */
package com.cooksys.friendlr.service;

import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.cooksys.friendlr.dto.PersonDTO;
import com.cooksys.friendlr.entity.Person;
import com.cooksys.friendlr.mapper.PersonMapper;

/**
 * @author Greg Hill
 */
@Service
public class PersonService {

	private TreeSet<Person> people;
	private static long index = 0;
	
	private PersonMapper personMapper;
	
	public PersonService(PersonMapper personMapper) {
		people = new TreeSet<Person>();
		this.personMapper = personMapper;
	}

	public TreeSet<PersonDTO> getPeople() {
		return personMapper.toPersonDTO(people);
	}

	public PersonDTO getPerson(Long id) {
		return personMapper
					.toPersonDTO(people
					.stream()
					.filter(person -> person.getId() == id)
					.findFirst()
					.orElse(null));
	}

	public Person getPersonEntity(Long id) {
		return people
					.stream()
					.filter(person -> person.getId() == id)
					.findFirst()
					.orElse(null);
	}

	public PersonDTO createPerson(PersonDTO personDTO) {
		personDTO.setId(index++);
		people.add(personMapper.toPerson(personDTO));
		
		return personDTO;
	}

	public PersonDTO replacePerson(Long id, PersonDTO personDTO) {
		// Deletes the person with the given id then assigns their id to the new person
		Person person = getPersonEntity(id);
		
		if(person != null) {
			person.setFirstName(personDTO.getFirstName());
			person.setLastName(personDTO.getLastName());
			return personMapper.toPersonDTO(person);
		}
		return null;
	}

	public PersonDTO editPerson(Long id, PersonDTO personDTO) {
		Person oldPerson = getPersonEntity(id);
		
		// Checks if a person with the given id exits
		if(oldPerson == null) {
			return null;			
		}
		
		// Checks that there is data for the first name
		if(personDTO.getFirstName() != null) {
			oldPerson.setFirstName(personDTO.getFirstName());
		}

		// Checks that there is data for the last name
		if(personDTO.getLastName() != null) {
			oldPerson.setLastName(personDTO.getLastName());
		}
		
		return getPerson(id);
	}

	public boolean deletePerson(Long id) {
		Person person = getPersonEntity(id);
		
		// Checks if the person exits and then if the remove operation was successful
		if(person != null) {
			person.getFriendList().forEach(friend -> {
				deleteFriend(person.getId(), friend.getId());
			});
			people.remove(person);
			return true;
		} else {
			return false;
		}
	}

	public TreeSet<PersonDTO> getFriends(Long id) {
		return personMapper.toPersonDTO(getPersonEntity(id).getFriendList());
	}

	public PersonDTO getFriend(Long id, Long friendId) {
		// If the friend exists in the user's friends list then we return the friend
		return personMapper
					.toPersonDTO(getPersonEntity(id)
					.getFriendList()
					.stream()
					.filter(friendInList -> friendId == friendInList.getId())
					.findFirst()
					.orElse(null));
	}

	public PersonDTO addFriend(Long id, Long friendId) {
		if(getPerson(id) != null && getPerson(friendId) != null) {
			getPersonEntity(id).getFriendList().add(getPersonEntity(friendId));
			getPersonEntity(friendId).getFriendList().add(getPersonEntity(id));
			return personMapper.toPersonDTO(getPersonEntity(friendId));
		}
		return null;
	}

	public boolean deleteFriend(Long id, Long friendId) {
		// Checks if the friend exits and then if the remove operation was successful
		if(getPersonEntity(id) != null && getPersonEntity(friendId) != null) {
			getPersonEntity(id).getFriendList().remove(getPersonEntity(id).getFriendList()
			.stream()
			.filter(friendInList -> friendId == friendInList.getId())
			.findFirst()
			.orElse(null));
			
			getPersonEntity(friendId).getFriendList().remove(getPersonEntity(friendId).getFriendList()
			.stream()
			.filter(person -> id == person.getId())
			.findFirst()
			.orElse(null));
			
			return true;
		}
		return false;
	}
	
}
