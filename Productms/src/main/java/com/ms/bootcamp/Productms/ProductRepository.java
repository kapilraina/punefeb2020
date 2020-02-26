package com.ms.bootcamp.Productms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.bootcamp.Productms.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
