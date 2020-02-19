package org.pap.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.pap.domain.Country;
import org.pap.domain.Hobby;
import org.pap.domain.Person;
import org.pap.helper.H;
import org.pap.repositories.RepositoryCountry;
import org.pap.repositories.RepositoryHobby;
import org.pap.repositories.RepositoryPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
	@Autowired
	private RepositoryPerson repoPerson;
	
	@Autowired
	private RepositoryCountry repoCountry;
	
	@Autowired
	private RepositoryHobby repoHobby;
	
	
	@GetMapping("")
	public String read(ModelMap m) {
		List<Person> people=repoPerson.findAll();
		m.put("people", people);
		
		m.put("view", "/view/person/read");
		return "/_t/frame";
	}
	
	@GetMapping("/create")
	public String create(ModelMap m) {
		List<Country> country = repoCountry.findAll();
		m.put("countries", country);
		List<Hobby> hobbies= repoHobby.findAll();
		m.put("hobbies", hobbies);
		
		m.put("view", "/view/person/create");
		return "_t/frame";
	}
	
	@PostMapping("/create")
	public String create(
			@RequestParam("name")String name,
			@RequestParam("nick")String nick,
			@RequestParam("password")String pwd,
			@RequestParam("born")Long born,
			@RequestParam(value = "likedHobbies[]", required=false)List<Long> likedHobbies,
			@RequestParam(value = "hatedHobbies[]", required=false)List<Long> hatedHobbies,
			HttpSession s,
			ModelMap m
			) {
		String route="/person";
		
		
		try {
			
			Country country = repoCountry.getOne(born);
			Person person = new Person(name,nick,pwd);
			country.getAreBorn().add(person);
			person.setBorn(country);
			likedHobbies = (likedHobbies == null?new ArrayList<Long>():likedHobbies);
			hatedHobbies = (hatedHobbies == null?new ArrayList<Long>():hatedHobbies);
			
			for (Long idHobby : likedHobbies) {
				Hobby hobby = repoHobby.getOne(idHobby);
				hobby.getPeopleWhoLiked().add(person);
				person.getLikedThings().add(hobby);
			}
			
			for (Long idHobby : hatedHobbies) {
				Hobby hobby= repoHobby.getOne(idHobby);
				hobby.getPeopleWhoHate().add(person);
				person.getHatedThings().add(hobby);
			}
			
			repoPerson.save(person);
			
			H.info(s, "person named "+name+" has been successfully created", "success", route);
		} catch (Exception e) {
			H.info(s, "person named "+name+" hasn't been properly created", "danger", route);
		}
		return "redirect:/info";
	}
	
	
	@GetMapping("/update")
	public String update(
			@RequestParam("id")Long id,
			ModelMap s) {
		s.put("person", repoPerson.getOne(id));
		s.put("countries", repoCountry.findAll());
		s.put("hobbies", repoHobby.findAll());
		
		s.put("view", "/view/person/update");
		return "_t/frame";
	}
	
	
	public void updatePostBorr(
			@RequestParam("id")Long id,
			@RequestParam("name")String name,
			@RequestParam("idP")Long idCountry,
			@RequestParam(value="likedThings[]", required=false) List<Long> liked,
			@RequestParam(value="hatedThings[]", required=false) List<Long> hated,
			HttpSession s
			) {
		try {
			
			
			Person person = repoPerson.getOne(id);
			person.setName(name);
			
			Country country = repoCountry.getOne(idCountry);
			
			country.getAreBorn().remove(person);
			person.setBorn(null);;
			
			country.getAreBorn().add(person);
			person.setBorn(country);
			
			for (Long long1 : liked) {
				Hobby hobby = repoHobby.getOne(long1);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@PostMapping("/delete")
	public String delete(
			@RequestParam("id")Long id,
			HttpSession s
			) {
		Person person = repoPerson.getOne(id);
		String name = person.getName();
		try {
			repoPerson.delete(person);
			H.info(s, "person named "+name+" has been sucessfully erased", "success", "/person" );
		} catch (Exception e) {
			H.info(s, "person named "+name+" has been sucessfully erased", "success", "/person");
		}
		return "redirect:/info";
	}
	
	
}
