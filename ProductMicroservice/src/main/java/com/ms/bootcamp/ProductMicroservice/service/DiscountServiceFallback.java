package com.ms.bootcamp.ProductMicroservice.service;

import org.springframework.stereotype.Component;

import com.ms.bootcamp.ProductMicroservice.model.DiscountRequest;
import com.ms.bootcamp.ProductMicroservice.model.DiscountResponse;

@Component
public class DiscountServiceFallback implements DiscountFeignProxy {

	@Override
	public DiscountResponse calculateDiscount(DiscountRequest request) {
		return new DiscountResponse(request.getCategory(), request.getMrp(), 0.0, 0.0, 0.0);
	}

}
