package com.microservices.CamelAPIGateway;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "gateway")
public class APIRouteBuilder extends RouteBuilder { 

	private String productmsendpoint="localhost:8081";

	private static final String REST_ENDPOINT = "http4:%s/product/v7/%s?httpClient.connectTimeout=1000"
			+ "&bridgeEndpoint=true&copyHeaders=true&connectionClose=true";

	@Override
	public void configure() throws Exception {
		
		  from("direct:productms").streamCaching().toF(REST_ENDPOINT,
		  productmsendpoint, "1")
				/*
				 * .log("Response from Product microservice:${body}").convertBodyTo(ProductDTO.
				 * class)
				 */.end();
		  
		  rest().get("/gateway").enableCORS(true).route()
				/*
				 * .multicast(AggregationStrategies.flexible().accumulateInCollection(ArrayList.
				 * class)) .parallelProcessing()
				 */.to("direct:productms").end().
		  marshal() .json(JsonLibrary.Jackson).convertBodyTo(String.class).endRest();
		 

	}

	public String getProductmsendpoint() {
		return productmsendpoint;
	}

	public void setProductmsendpoint(String productmsendpoint) {
		this.productmsendpoint = productmsendpoint;
	}

}
