/**
 * 
 */
package com.cooksys.friendlr.mapper;

import java.util.TreeSet;

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
	
	public TreeSet<PersonDTO> toPersonDTO(TreeSet<Person> people);
	
	public TreeSet<Person> toPerson(TreeSet<PersonDTO> peopleDTO);
}
