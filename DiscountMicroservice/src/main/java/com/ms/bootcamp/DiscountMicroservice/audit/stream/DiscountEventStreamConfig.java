package com.ms.bootcamp.discountmicroservice.audit.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(DiscountStream.class)
public class DiscountEventStreamConfig {

}
