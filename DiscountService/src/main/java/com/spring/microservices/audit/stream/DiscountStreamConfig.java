package com.spring.microservices.audit.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(DiscountStream.class)
public class DiscountStreamConfig {

}
