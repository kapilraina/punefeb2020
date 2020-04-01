package com.ms.bootcamp.productmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class GenericController {
	@Autowired
	MinionsLibrary library;
	
	@Value("${greeting:Hi}")
	String greeting;

	@RequestMapping(path = "/greet/{name}", method = RequestMethod.GET)
	public String greet(@PathVariable String name) {
		return greeting + " " + name;

	}

	@RequestMapping("/minion/fragment/{name}")
	public String getMinion(@PathVariable String name) {

		return library.getMinion(name);
	}

}
