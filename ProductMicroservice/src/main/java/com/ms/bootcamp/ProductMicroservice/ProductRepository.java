package com.ms.bootcamp.productmicroservice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.bootcamp.productmicroservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
