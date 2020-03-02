package com.ms.training.SimpleWebAuditMS;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AuditStream {

	
	final String INPUT = "webaudit-in";

 
	@Input(INPUT)
	SubscribableChannel inboundAuditChannel();

}
