package org.pap.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.pap.domain.Country;
import org.pap.helper.H;
import org.pap.repositories.RepositoryCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/country")
public class CountryController {
	
	@Autowired
	private RepositoryCountry repoCountry;
	
	@GetMapping("")
	public String read(ModelMap data) {
		List<Country>countries = repoCountry.findAll();
		data.put("countries", countries);
		
		data.put("view", "/view/country/read");
		return "_t/frame";
	}
	
	@GetMapping("/create")
	public String createGet(ModelMap m, HttpSession s) {
		try {
			s.getAttribute("person");
			m.put("view", "/view/country/create");
		} catch (Exception e) {
			m.put("view", "/view/anonymous/home");
		}
		
		return"/_t/frame";
	}
	
	@PostMapping("/create")
	public String createPost(
			@RequestParam("name")String name, HttpSession s) {
		try {
			repoCountry.save(new Country(name));
			H.info(s, "Country "+name+" created correctly", "info", "/country");
		} catch (Exception e) {
			H.info(s, "Country "+name+" duplicated", "danger", "/country");
		}
		
		return "redirect:/info";
	}
	
	@GetMapping("/update")
	public String update(
			@RequestParam("id")Long id,
			ModelMap m) {
		Country country = repoCountry.getOne(id);
		m.put("country", country);
		
		m.put("view", "/view/country/update");
		return "_t/frame";
	}
	
	@PostMapping("/update")
	public String update(
			@RequestParam("name")String name,
			@RequestParam("id")Long id,
			ModelMap m,
			HttpSession s) {
			Country country = repoCountry.getOne(id);	
			try {
				country.setName(name);
				repoCountry.save(country);
			H.info(s, "country "+name+" has been updated", "success", "/country");
			} catch (Exception e) {
			H.info(s, "The selected country "+name+" hasn't been sucessfully updated", "danger", "/country");	
			}
			return "redirect:/info";
	}
	
	
	@PostMapping("/delete")
	public String Delete(@RequestParam("id")Long identifier, HttpSession s) {
		Country country = repoCountry.getOne(identifier);
		String name = country.getName();
		try {
			if (identifier!=null) {
		repoCountry.delete(country);
		H.info(s, "Country "+name+" deleted correctly", "info", "/country");
		}
		} catch (Exception e) {
			H.info(s, "Error deliting country "+name, "danger", "/country/create");
		}
		return "redirect:/info";
	}

}

	
