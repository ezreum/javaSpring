package org.pap.controller;

import java.util.List;

import org.pap.domain.Country;
import org.pap.domain.Hobby;
import org.pap.repositories.RepositoryCountry;
import org.pap.repositories.RepositoryHobby;
import org.pap.repositories.RepositoryPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
	@Autowired
	private RepositoryPerson repoPerson;
	
	@Autowired
	private RepositoryCountry repoCountry;
	
	@Autowired
	private RepositoryHobby repoHobby;
	
	@GetMapping("/create")
	public String create(ModelMap m) {
		List<Country> country = repoCountry.findAll();
		m.put("countries", country);
		List<Hobby> hobby= repoHobby.findAll();
		m.put("hobbies", hobby);
		
		m.put("view", "/view/person/create");
		return "_t/frame";
	}
	
}
