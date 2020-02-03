package org.pap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne
	private Country born;
	
	private Collection<Hobby> likedThings;
	
	private Collection<Hobby> hatedThings;
	
	//===================
	public Person() {
		this.likedThings = new ArrayList<Hobby>();
		this.hatedThings = new ArrayList<Hobby>();
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

	public Country getBorn() {
		return born;
	}

	public void setBorn(Country born) {
		this.born = born;
	}

	public Collection<Hobby> getLikedThings() {
		return likedThings;
	}

	public void setLikedThings(Collection<Hobby> likedThings) {
		this.likedThings = likedThings;
	}

	public Collection<Hobby> getHatedThings() {
		return hatedThings;
	}

	public void setHatedThings(Collection<Hobby> hatedThings) {
		this.hatedThings = hatedThings;
	}
	
	
	
}


