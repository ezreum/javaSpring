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
		
		m.put("view", "/view/hobby/read");
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
	
	@GetMapping("/update")
	public String update(ModelMap m,
			@RequestParam("id")Long id
			) {
		Hobby hobby = repoHobby.getOne(id);
		m.put("hobby", hobby);
		
		m.put("view", "/view/hobby/update");
		return "/_t/frame";
	}
	
	
	@PostMapping("/update")
	public String updatePost(
			@RequestParam("name")String name,
			@RequestParam("id")Long id,
			ModelMap m,
			HttpSession s) {
		Hobby hobby = repoHobby.getOne(id);
		hobby.setName(name);
	try {
		repoHobby.save(hobby);
		H.info(s, "hobby "+name+" has been successfully updated", 
				"success", "/hobby");
	} catch (Exception e) {
		H.info(s, "hobby named "+name+" has not been"
				+ " properly updated", "danger", "/hobby");
	}
	return	"redirect:/info";
	}
	
	@PostMapping("/delete")
	public String delete(
			@RequestParam("id")Long id,
			ModelMap m,
			HttpSession s
			) {
		Hobby hobby = repoHobby.getOne(id);
		String name = hobby.getName();
		try {
			repoHobby.delete(hobby);
			H.info(s, "hobby "+name+ " has been successfully deleted", "success", "/hobby");
		} catch (Exception e) {
			H.info(s, "hobby "+name+" has not been deleted", "warning", "/hobby/read");
		}
		
		return "redirect:/info";
	}
	
	
}
