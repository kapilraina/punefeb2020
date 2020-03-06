package com.microservices.microprofile.discountms.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.microprofile.discountms.model.DiscountRequest;
import com.microservices.microprofile.discountms.model.DiscountResponse;
import com.microservices.microprofile.discountms.model.ProductCategory;

@Path("/api")
public class DiscountController {

	@Inject
	@ConfigProperty(name = "discounthost")
	private String discounthost;

	@Inject
	@ConfigProperty(name = "discountport")
	private int discountport;

	@Path("/getdiscount/{category}/{price}")
	@GET
	@Produces("text/plain")
	public String getdiscount(@PathParam("category") ProductCategory category, @PathParam("price") double price)
			throws InterruptedException, ExecutionException, JsonProcessingException {

		String backendServiceUrl = String.format("http://%s:%d", discounthost, discountport);
		System.out.println("Sending Discount Request To :" + backendServiceUrl);
		DiscountRequest discountRequest = new DiscountRequest();
		discountRequest.setCategory(category);
		//discountRequest.setCategory(ProductCategory.valueOf(category));
		discountRequest.setMrp(price);

		Entity<DiscountRequest> discountRequestEntity = Entity.json(discountRequest);
		Client client = ClientBuilder.newClient();
		Future<DiscountResponse> discountResponseFuture = client.target(backendServiceUrl).path("caldisc").request()
				.buildPost(discountRequestEntity).submit(DiscountResponse.class);
		DiscountResponse discountResponse = discountResponseFuture.get();
		ObjectMapper mapper = new ObjectMapper();
		String responseJson = mapper.writeValueAsString(discountResponse);
		return responseJson;

	}

}
