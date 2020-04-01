package com.ms.bootcamp.productmicroservice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.bootcamp.productmicroservice.model.ProductTag;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer>{
	

}
