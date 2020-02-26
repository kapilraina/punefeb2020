package com.ms.bootcamp.Productms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.bootcamp.Productms.ProductRepository;
import com.ms.bootcamp.Productms.model.Product;

@Component
public class ProductService {
	
	@Autowired
	ProductRepository repo;
	
	public List<Product> getAllProducts()
	{
		return repo.findAll();
	}
	

	public Optional<Product> getProduct(Integer id)
	{
		return repo.findById(id);
	}


}
