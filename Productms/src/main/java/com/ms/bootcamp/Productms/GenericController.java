package com.ms.bootcamp.Productms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class GenericController {
	@Autowired
	MinionsLibrary library;

	@RequestMapping(path = "/greet/{name}", method = RequestMethod.GET)
	public String greet(@PathVariable String name) {
		return "Hello - " + name;

	}

	@RequestMapping("/minion/fragment/{name}")
	public String getMinion(@PathVariable String name) {

		return library.getMinion(name);
	}

}
