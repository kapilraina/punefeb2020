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
import org.springframework.transaction.annotation.Transactional;

import com.ms.bootcamp.productmicroservice.model.Product;
import com.ms.bootcamp.productmicroservice.model.ProductCategory;
import com.ms.bootcamp.productmicroservice.model.ProductTag;

@SpringBootApplication
//@EnableEurekaClient
@EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableFeignClients
public class ProductMicroserviceApplication {

	@Autowired
	private ProductRepository prepo;
	@Autowired
	private ProductTagRepository ptrepo;
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
			seedItUp();

		};
	}

	@Transactional
	public void seedItUp() {
		Product p = new Product(1, "Kitchen Chimney", "6x4. Non-exhaust", ProductCategory.KITCHENELECTRONIC, 200.87);
		productSeeds.put(1, p);
		prepo.save(p);
		p = new Product(2, "Persian Carpet", "9x9. Handwoven", ProductCategory.FURNISHING, 1000.45);
		productSeeds.put(2, p);
		prepo.save(p);
		p = new Product(3, "Space Craft Lego", "580 pieces", ProductCategory.TOY, 776.00);
		productSeeds.put(3, p);
		prepo.save(p);

		ptrepo.save(new ProductTag(3, "plastic"));
		ptrepo.save(new ProductTag(3, "blocks"));
		ptrepo.save(new ProductTag(3, "shapes"));
		ptrepo.save(new ProductTag(2, "wool"));
		ptrepo.save(new ProductTag(2, "royal"));
		ptrepo.save(new ProductTag(2, "living"));
		ptrepo.save(new ProductTag(1, "kitchen"));
		ptrepo.save(new ProductTag(1, "fresh"));
		ptrepo.save(new ProductTag(1, "carbon"));
	}
  
}
