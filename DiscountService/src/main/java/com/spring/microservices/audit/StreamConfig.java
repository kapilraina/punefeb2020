package com.spring.microservices.audit;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(AuditStream.class)
public class StreamConfig {

}
