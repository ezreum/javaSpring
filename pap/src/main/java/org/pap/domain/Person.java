package org.pap.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Column(unique = true)
	private String nick;
	
	private String pwd;
	
	@ManyToOne
	private Country born;
	@ManyToMany
	private Collection<Hobby> likedThings;
	@ManyToMany
	private Collection<Hobby> hatedThings;
	
	//===================
	public Person() {
		this.likedThings = new ArrayList<Hobby>();
		this.hatedThings = new ArrayList<Hobby>();
	}
	
	public Person(String name,String pass, String nick) {
		this.name=name;
		this.nick=nick;
		this.pwd = (new BCryptPasswordEncoder()).encode(pass);
		this.likedThings=new ArrayList<Hobby>();
		this.hatedThings=new ArrayList<Hobby>();
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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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


