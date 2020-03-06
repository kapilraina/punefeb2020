package com.microservices.microprofile.ThornTailDemo.rest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/api")
public class GreetingController {

	@Inject
	@ConfigProperty(name = "greetingapp.greetingname")
	private String greetingname;

	@Path("/greeting")
	@GET
	@Produces("text/plain")
	public String greetIt() {
		String hostName = "unknown";
		try {
			hostName = Inet4Address.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return greetingname + ", " + hostName + " @ " + Calendar.getInstance().getTime();
	}

}
