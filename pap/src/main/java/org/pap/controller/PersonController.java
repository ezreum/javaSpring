package org.pap.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.pap.domain.Country;
import org.pap.domain.Hobby;
import org.pap.domain.Person;
import org.pap.repositories.RepositoryCountry;
import org.pap.repositories.RepositoryHobby;
import org.pap.repositories.RepositoryPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
		List<Hobby> hobby= repoHobby.findAll();
		m.put("hobbies", hobby);
		
		m.put("view", "/view/person/create");
		return "_t/frame";
	}
	
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
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
	}
	
	
}
