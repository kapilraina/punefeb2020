package com.ms.bootcamp.Productms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ms.bootcamp.Productms.model.Product;
import com.ms.bootcamp.Productms.model.ProductCategory;

@SpringBootApplication
public class ProductmsApplication {

	@Autowired
	ProductRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(ProductmsApplication.class, args);
	}

	@Bean
	CommandLineRunner commandlinerunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("BEAN COUNT : " + ctx.getBeanDefinitionCount());
			Product p = new Product(1, "Space Ship Lego", "It is fantasy logo to build a space ship",
					ProductCategory.TOY, 100.00);
			repo.save(p);
			
			p = new Product(2, "Kitchen Chimney", "4' x 4' Carbon Mesh kitchen chimney",
					ProductCategory.KITCHENELECTRONIC, 200.00);
			repo.save(p);
			
			p = new Product(3, "Bass Guitar", "6 Copper String Bass Guitar made of walnut wood",
					ProductCategory.MUSICINSTRUMENT, 76.77);
			repo.save(p);
			
		};
	}

}
