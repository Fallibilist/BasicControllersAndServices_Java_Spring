/**
 * 
 */
package com.cooksys.friendlr.service;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.friendlr.dto.PersonDTO;
import com.cooksys.friendlr.entity.Person;
import com.cooksys.friendlr.mapper.PersonMapper;

/**
 * @author Greg Hill
 */
@Service
public class PersonService {

	private Set<Person> people;
	private static long index = 0;
	
	private PersonMapper personMapper;
	
	public PersonService(PersonMapper personMapper) {
		people = new TreeSet<Person>();
		this.personMapper = personMapper;
	}

	public Set<PersonDTO> getPeople() {
		return people.stream().map(personMapper::toPersonDTO).collect(Collectors.toSet());
	}

	public PersonDTO getPerson(Long id) {
		for(Person person : people) {
			if(person.getId() == id) {
				return personMapper.toPersonDTO(person);
			}
		}
		return null;
	}

	public Person getPersonEntity(Long id) {
		for(Person person : people) {
			if(person.getId() == id) {
				return person;
			}
		}
		return null;
	}

	public PersonDTO createPerson(PersonDTO personDTO) {
		personDTO.setId(index++);
		people.add(personMapper.toPerson(personDTO));
		
		return personDTO;
	}

	public PersonDTO replacePerson(Long id, PersonDTO personDTO) {
		Person oldPerson = getPersonEntity(id);
		
		// Checks if a person with the given id exits
		if(oldPerson == null) {
			return null;			
		}
		
		// Overwrites the person's fields
		oldPerson.setFirstName(personDTO.getFirstName());
		oldPerson.setLastName(personDTO.getLastName());
		
		return personDTO;
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
		if(person != null && people.remove(person)) {
			return true;
		} else {
			return false;
		}
	}

	public Set<PersonDTO> getFriends(Long id) {
		return personMapper.toPersonDTO(getPersonEntity(id).getFriendList());
	}

	public PersonDTO getFriend(Long id, Long friendId) {
		// If the friend exists in the user's friends list then we return the friend
		for(Person friendInList : getPersonEntity(id).getFriendList()) {
			if(friendId == friendInList.getId()) {
				return personMapper.toPersonDTO(friendInList);
			}
		}
		return null;
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
		if(getPersonEntity(id) != null) {
			for(Person friendInList : getPersonEntity(id).getFriendList()) {
				if(friendId == friendInList.getId()) {
					getPersonEntity(id).getFriendList().remove(friendInList);
					for(Person person : getPersonEntity(friendId).getFriendList()) {
						if(id == person.getId()) {
							getPersonEntity(friendId).getFriendList().remove(person);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
}
