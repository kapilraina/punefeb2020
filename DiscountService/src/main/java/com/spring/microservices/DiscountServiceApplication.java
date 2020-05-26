package com.spring.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
public class DiscountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountServiceApplication.class, args);
	}

}
