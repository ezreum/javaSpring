package org.pap.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.pap.domain.Person;
import org.pap.helper.H;
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
	private RepositoryCountry repoPais;

	@Autowired
	private RepositoryHobby repoAficion;

	// ===============================================

	@GetMapping("/")
	public String home(ModelMap m) {
		m.put("view", "/view/anonymous/home");
		return "/_t/frame";
	}

	@GetMapping("/info")
	public String info(HttpSession s, ModelMap m) {
		String mensaje = s.getAttribute("mensaje") != null ? (String) s.getAttribute("mensaje")
				: "Pulsa para volver a home";
		String severity = s.getAttribute("severity") != null ? (String) s.getAttribute("severity") : "info";
		String link = s.getAttribute("link") != null ? (String) s.getAttribute("link") : "/";

		s.removeAttribute("mensaje");
		s.removeAttribute("severity");
		s.removeAttribute("link");

		m.put("mensaje", mensaje);
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
		String route = "/";
		Person person = repoPerson.findByNick(nick);
		try {
			if( !new BCryptPasswordEncoder().matches(pwd, person.getPwd()) ) {
				throw new Exception("Contraseña o nick incorrectos");
			}
			s.setAttribute("person", person);
			
		}catch (EntityNotFoundException e) {
			H.info(s, e.getMessage(), "warning", "/login");
			route="/info";
		}
		
		
		return "redirect:"+route;
	}

	
	
}