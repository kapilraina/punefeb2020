package com.ms.bootcamp.Productms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductmsApplication.class, args);
	}

	@Bean
	CommandLineRunner commandlinerunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("BEAN COUNT : " + ctx.getBeanDefinitionCount());
			for (int x = 0; x < ctx.getBeanDefinitionNames().length; x++) {
				//System.out.println(ctx.getBeanDefinitionNames()[x]);
			}

		};
	}

}
