package org.pap.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.pap.domain.Hobby;
import org.pap.exceptions.DangerException;
import org.pap.exceptions.InfoException;
import org.pap.helper.H;
import org.pap.helper.PRG;
import org.pap.helper.RolHelper;
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
	public String create(ModelMap m, HttpSession s) throws DangerException {
		RolHelper.isRolOK("auth", s);
	m.put("view", "/view/hobby/create");
	return	"/_t/frame";
	}
	
	@PostMapping("/create")
	public void createPost(
			@RequestParam("name")String name,
			ModelMap m,
			HttpSession s) throws DangerException, InfoException {
	try {
		repoHobby.save(new Hobby(name));
	} catch (Exception e) {
		PRG.error("hobby named "+name+" has not been properly created", "/hobby");
	}
	PRG.info("hobby "+name+" has been successfully created", "/hobby");
	}
	
	@GetMapping("/update")
	public String update(ModelMap m,
			@RequestParam("id")Long id,
			HttpSession s
			) throws DangerException {
		RolHelper.isRolOK("auth", s);
		Hobby hobby = repoHobby.getOne(id);
		m.put("hobby", hobby);
		
		m.put("view", "/view/hobby/update");
		return "/_t/frame";
	}
	
	
	@PostMapping("/update")
	public void updatePost(
			@RequestParam("name")String name,
			@RequestParam("id")Long id,
			ModelMap m,
			HttpSession s) throws DangerException, InfoException {
		Hobby hobby = repoHobby.getOne(id);
		hobby.setName(name);
	try {
		repoHobby.save(hobby);
		
	} catch (Exception e) {
		PRG.error("hobby named "+name+" has not been properly updated", "/hobby");
	}
	PRG.info("hobby "+name+" has been successfully updated", "/hobby");
	}
	
	@PostMapping("/delete")
	public void delete(
			@RequestParam("id")Long id,
			ModelMap m,
			HttpSession s
			) throws DangerException, InfoException {
		RolHelper.isRolOK("auth", s);
		Hobby hobby = repoHobby.getOne(id);
		String name = hobby.getName();
		try {
			repoHobby.delete(hobby);
			
		} catch (Exception e) {
			PRG.error("hobby "+name+" has not been deleted", "/hobby/read");
		}
		PRG.info("hobby "+name+ " has been successfully deleted", "/hobby");
	}
	
	
}
