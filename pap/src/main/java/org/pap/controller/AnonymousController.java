package org.pap.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.pap.domain.Person;
import org.pap.exceptions.DangerException;
import org.pap.helper.H;
import org.pap.helper.PRG;
import org.pap.helper.RolHelper;
import org.pap.repositories.RepositoryCountry;
import org.pap.repositories.RepositoryHobby;
import org.pap.repositories.RepositoryPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnonymousController {

	// ===============================================


	@Autowired
	private RepositoryPerson repoPerson;

	@Autowired
	private RepositoryCountry repoCountry;

	@Autowired
	private RepositoryHobby repoHobby;

	// ===============================================

	@GetMapping("/")
	public String home(ModelMap m) {
		m.put("view", "/view/anonymous/home");
		return "/_t/frame";
	}

	@GetMapping("/info")
	public String info(HttpSession s, ModelMap m) {
		String mensaje = s.getAttribute("_text") != null ? (String) s.getAttribute("_text")
				: "Go back home";
		String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
		String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

		s.removeAttribute("_text");
		s.removeAttribute("_severity");
		s.removeAttribute("_link");

		m.put("text", mensaje);
		m.put("severity", severity);
		m.put("link", link);

		m.put("view", "/_t/info");
		return "/_t/frame";
	}
	
	@GetMapping("/login")
	public String login(ModelMap m) {
		
		m.put("view", "/view/anonymous/login");
		return "/_t/frame";
	}
	
	@PostMapping("/login")
	public String login(ModelMap m,
			@RequestParam("nick")String nick,
			@RequestParam("pwd")String pwd,
			HttpSession s
			) throws Exception {
		RolHelper.isRolOK("anonymous", s);
		String route = "/";
		Person person = repoPerson.findByNick(nick);
		try {
			if( !new BCryptPasswordEncoder().matches(pwd, person.getPwd()) ) {
				throw new Exception("Contraseña o nick incorrectos");
			}
			s.setAttribute("person", person);
			
		}catch (EntityNotFoundException e) {
			PRG.error(e.getMessage(), "/login");
		}
		return "redirect:"+route;
	}

	@GetMapping("/init")
	public String init(ModelMap m) throws DangerException {
		if (repoPerson.findByNick("admin") != null) {
			PRG.error("BD no vacía");
		}
		m.put("view", "/view/anonymous/init");
		
		
		return "/_t/frame";
	}
	
	@PostMapping("/init")
	public String init(@RequestParam("pwd")String pwd,
			ModelMap m
			) throws DangerException {
		if (repoPerson.findByNick("admin") != null) {
			PRG.error("incorrect mapping. BD is not empty");
		}
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		if (!bpe.matches(pwd, bpe.encode(pwd))) { // Password harcoded
			PRG.error("Password incorrecta","/init");
		}
		repoPerson.deleteAll();
		repoCountry.deleteAll();
		repoHobby.deleteAll();
		repoPerson.save(new Person("Administrator",pwd,"admin",LocalDate.now()));
		return "redirect:/";
	}
	
}