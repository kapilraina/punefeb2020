package com.ms.bootcamp.productmicroservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ms.bootcamp.productmicroservice.model.Product;
import com.ms.bootcamp.productmicroservice.model.ProductCategory;
import com.ms.bootcamp.productmicroservice.model.ProductTag;
import com.ms.bootcamp.productmicroservice.repository.ProductRepository;

@SpringBootApplication
//@EnableEurekaClient
@EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableFeignClients
public class ProductMicroserviceApplication {

	@Autowired
	private ProductRepository prepo;

	public Map<Integer, Product> productSeeds = new HashMap<Integer, Product>();

	@Bean
	Map<Integer, Product> productSeeds() {
		return productSeeds;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			Product p = new Product(1, "Kitchen Chimney", "6x4. Non-exhaust", ProductCategory.KITCHENELECTRONIC,
					200.87);
			p.getTags().add(new ProductTag(1, "kitchen"));
			p.getTags().add(new ProductTag(1, "filter"));
			p.getTags().add(new ProductTag(1, "smoke"));

			prepo.save(p);
			p = new Product(2, "Persian Carpet", "9x9. Handwoven", ProductCategory.FURNISHING, 1000.45);
			p.getTags().add(new ProductTag(2, "wool"));
			p.getTags().add(new ProductTag(2, "draping"));
			p.getTags().add(new ProductTag(2, "classy"));

			prepo.save(p);
			p = new Product(3, "Space Craft Lego", "580 pieces", ProductCategory.TOY, 776.00);
			p.getTags().add(new ProductTag(3, "plastic"));
			p.getTags().add(new ProductTag(3, "innovation"));
			p.getTags().add(new ProductTag(3, "blocks"));

			prepo.save(p);

		};
	}
}
