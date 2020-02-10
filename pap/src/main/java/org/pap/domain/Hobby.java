package org.pap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Hobby {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "likedThings")
	private Collection<Person> peopleWhoLike;
	
	@ManyToMany(mappedBy = "hatedThings")
	private Collection<Person> peopleWhoHate;
	
	//========
	public Hobby() {
		this.peopleWhoLike = new ArrayList<Person>();
		this.peopleWhoHate = new ArrayList<Person>();
	}


	public Hobby(String name) {
	this.name=name;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Collection<Person> getPeopleWhoLiked() {
		return peopleWhoLike;
	}


	public void setPeopleWhoLiked(Collection<Person> peopleWhoLiked) {
		this.peopleWhoLike = peopleWhoLiked;
	}


	public Collection<Person> getPeopleWhoHate() {
		return peopleWhoHate;
	}


	public void setPeopleWhoHate(Collection<Person> peopleWhoHate) {
		this.peopleWhoHate = peopleWhoHate;
	}
	
	
	
	}
