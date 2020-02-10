package org.pap.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.pap.domain.Hobby;
import org.pap.helper.H;
import org.pap.repositories.RepositoryHobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/hobby")
public class HobbyController {

	@Autowired
	private RepositoryHobby repoHobby;
	
	@GetMapping("")
	public String read(ModelMap m) {
		List<Hobby> hobbies = repoHobby.findAll();
		m.put("hobbies", hobbies);
		
		m.put("view", "/view/hobby/create");
		return "/_t/frame";
	}
	
	@GetMapping("/create")
	public String create(ModelMap m) {
	
	m.put("view", "/view/hobby/create");
	return	"/_t/frame";
	}
	
	@PostMapping("/create")
	public String createPost(
			@RequestParam("name")String name,
			ModelMap m,
			HttpSession s) {
	try {
		repoHobby.save(new Hobby(name));
		H.info(s, "hobby "+name+" has been successfully created", 
				"success", "/hobby");
	} catch (Exception e) {
		H.info(s, "hobby named "+name+" has not been"
				+ " properly created", "danger", "/hobby");
	}
	return	"redirect:/info";
	}
	
	
	
	
	
	
}
