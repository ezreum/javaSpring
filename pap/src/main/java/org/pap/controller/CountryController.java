package org.pap.controller;

import java.util.List;

import org.pap.domain.Country;
import org.pap.repositories.RepositoryCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CountryController {
	
	@Autowired
	private RepositoryCountry repoCountry;
	
	@GetMapping("/country")
	public String read(ModelMap data) {
		List<Country>countries = repoCountry.findAll();
		data.put("countries", countries);
		
		data.put("view", "/view/country/read");
		return "_t/frame";
	}
	
	@GetMapping("/country/create")
	public String createGet() {
		return"view/country/create";
	}
	
	@PostMapping("/country/createPost")
	public String createPost(
			@RequestParam("name")String name) {
		Country country = new Country();
		country.setName(name);
		repoCountry.save(country);
		return"view/country/createPost";
	}
	
}

	
