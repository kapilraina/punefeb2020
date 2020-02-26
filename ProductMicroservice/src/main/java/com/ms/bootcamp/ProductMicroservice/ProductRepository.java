package com.ms.bootcamp.ProductMicroservice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.bootcamp.ProductMicroservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
