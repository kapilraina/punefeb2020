package com.ms.bootcamp.ProductMicroservice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.bootcamp.ProductMicroservice.model.ProductTag;

public interface ProductTagRepository extends JpaRepository<ProductTag, Integer>{
	

}
