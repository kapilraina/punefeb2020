package com.ms.bootcamp.ProductMicroservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ms.bootcamp.ProductMicroservice.ProductRepository;
import com.ms.bootcamp.ProductMicroservice.model.Product;

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

	@Transactional
	public void save(Product p)
	{
		repo.save(p);
	}

}
