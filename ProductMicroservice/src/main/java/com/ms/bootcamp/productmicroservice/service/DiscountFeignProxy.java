package com.ms.bootcamp.productmicroservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ms.bootcamp.productmicroservice.model.DiscountRequest;
import com.ms.bootcamp.productmicroservice.model.DiscountResponse;

@FeignClient(name = "discountms", fallback = DiscountServiceFallback.class)
public interface DiscountFeignProxy {
	@RequestMapping(value = "/caldisc", method = RequestMethod.POST)
	public DiscountResponse calculateDiscount(DiscountRequest request);

}
