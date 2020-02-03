package org.pap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	@OneToMany(mappedBy = "born")
	private Collection<Person> areBorn;
	
	//====
	public Country() {
		this.areBorn = new ArrayList<Person>();
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


	public Collection<Person> getAreBorn() {
		return areBorn;
	}


	public void setAreBorn(Collection<Person> areBorn) {
		this.areBorn = areBorn;
	}
	
	
	
}
