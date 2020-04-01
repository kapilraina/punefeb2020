package com.ms.bootcamp.productmicroservice.service;

import org.springframework.stereotype.Component;

import com.ms.bootcamp.productmicroservice.model.DiscountRequest;
import com.ms.bootcamp.productmicroservice.model.DiscountResponse;

@Component
public class DiscountServiceFallback implements DiscountFeignProxy {

	@Override
	public DiscountResponse calculateDiscount(DiscountRequest request) {
		return new DiscountResponse(request.getCategory(), request.getMrp(), request.getMrp(), 0.0, 0.0);
	}

}
