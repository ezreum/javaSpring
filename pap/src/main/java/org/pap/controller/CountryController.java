package org.pap.controller;

import org.pap.domain.Country;
import org.pap.repositories.RepositoryCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CountryController {
	
	@Autowired
	private RepositoryCountry repoCountry;
	
	@GetMapping("/country/create")
	public String createGet() {
		return"view/country/create";
	}
	
	public String createPost(
			@RequestParam("name")String name) {
		Country country = new Country();
		country.setName(name);
		repoCountry.save(country);
		return"view/country/createPost";
	}
	
	public String read(ModelMap m) {
		Country country = new Country();
		//	country.setName();
		repoCountry.save(country);
		return"view/country/createPost";
	}
	
}

	
