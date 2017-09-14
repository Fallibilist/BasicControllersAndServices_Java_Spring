/**
 * 
 */
package com.cooksys.friendlr.entity;

import java.util.TreeSet;

/**
 * @author Greg Hill
 */
public class Person implements Comparable<Person> {
	
	private Long id;
	private String firstName;
	private String lastName;
	private TreeSet<Person> friendList;
	
	/**
	 * @return the friendIds
	 */
	public TreeSet<Person> getFriendList() {
		return friendList;
	}

	/**
	 * @param friendIds the friendIds to set
	 */
	public void setFriendList(TreeSet<Person> friendIds) {
		this.friendList = friendIds;
	}

	public Person() {
		friendList = new TreeSet<Person>();
	}
	
	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 */
	public Person(Long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param person object to compare this person to for sorting
	 */
	@Override
	public int compareTo(Person person) {
		if(id < person.getId()) {
			return -1;
		} else if(id == person.getId()) {
			return 0;
		} else {
			return 1;
		}
	}
}
