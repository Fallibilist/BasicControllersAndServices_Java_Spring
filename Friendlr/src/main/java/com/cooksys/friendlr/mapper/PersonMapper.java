/**
 * 
 */
package com.cooksys.friendlr.mapper;

import java.util.Set;

import org.mapstruct.Mapper;

import com.cooksys.friendlr.dto.PersonDTO;
import com.cooksys.friendlr.entity.Person;

/**
 * @author Greg Hill
 *
 */

@Mapper(componentModel="spring")
public interface PersonMapper {
	
	public PersonDTO toPersonDTO(Person person);
	
	public Person toPerson(PersonDTO personDTO);
	
	public Set<PersonDTO> toPersonDTO(Set<Person> people);
	
	public Set<Person> toPerson(Set<PersonDTO> peopleDTO);
}
