package com.ms.bootcamp.ProductMicroservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ms.bootcamp.ProductMicroservice.model.Product;
import com.ms.bootcamp.ProductMicroservice.model.ProductDTO;
import com.ms.bootcamp.ProductMicroservice.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getOneProduct(@PathVariable int id) {
		Optional<Product> p = productService.getProduct(id);
		if (p.isPresent())
			return new ResponseEntity<Product>(p.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);

	}

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public ResponseEntity<Product> saveProduct(@RequestBody Product p) {
		productService.save(p);
		return new ResponseEntity<Product>(HttpStatus.CREATED);
	}

	/**
	 * Use generic Discovery Client
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/v2/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductv2(@PathVariable int id) {
		ProductDTO pdto = productService.getProductv2(id);
		if (pdto != null) {
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Use Eureka Client
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/v3/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductv3(@PathVariable int id) {
		ProductDTO pdto = productService.getProductv3(id);
		if (pdto != null) {
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Using the Ribbon API Directly
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/v4/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductv4(@PathVariable(name = "id") Integer id) {
		ProductDTO pdto = productService.getProductv4(id);
		if (pdto != null) {
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Using the Load Balancer Rest
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/v5/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductv5(@PathVariable(name = "id") Integer id) {
		ProductDTO pdto = productService.getProductv5(id);
		if (pdto != null) {
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * LB+Hystrix
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/v6/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductv6(@PathVariable(name = "id") Integer id) {
		
		ProductDTO pdto = productService.applyDiscount(productService.getProductv6(id));
		if (pdto != null) {
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
 
	}
}
