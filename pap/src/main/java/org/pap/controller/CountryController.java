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
	public String createGet() {
		return"/view/country/create";
	}
	
	@PostMapping("/createPost")
	public String createPost(
			@RequestParam("name")String name) {
		repoCountry.save(new Country(name));
		return"/view/country/createPost";
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

	
