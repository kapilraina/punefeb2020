package com.ms.bootcamp.Productms;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericController {

	@RequestMapping(path = "/greet", method = RequestMethod.GET)
	public String greet() {
		return "Hello @ " + new Date();

	}

}
