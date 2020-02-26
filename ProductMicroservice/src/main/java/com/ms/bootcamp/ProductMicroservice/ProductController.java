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
		Optional<Product> p= productService.getProduct(id); 
		if(p.isPresent())
			return new ResponseEntity<Product>(p.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		
	}
	
	@RequestMapping(path = "/save",method = RequestMethod.POST)
	public ResponseEntity<Product> saveProduct(@RequestBody Product p)
	{
		productService.save(p);
		return new ResponseEntity<Product>(HttpStatus.CREATED);
	}

}
