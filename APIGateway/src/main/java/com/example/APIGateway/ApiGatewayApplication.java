package com.example.APIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	
	/*
	 * public RouteLocator customRouteLocator(RouteLocatorBuilder builder) { return
	 * builder.routes().route(r ->
	 * r.path("/api/eureka/**").uri("lb://SPRING-CLOUD-EUREKA-CLIENT")).build(); }
	 */

}