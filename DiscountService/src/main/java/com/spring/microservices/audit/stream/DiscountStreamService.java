package com.spring.microservices.audit.stream;

import java.util.concurrent.Executors;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class DiscountStreamService {

	private final DiscountStream discountStream;

	public DiscountStreamService(DiscountStream discountStream) {
		this.discountStream = discountStream;
	}

	public void pubAuditEvent(final String auditEventPayload) {
		// log.info("Sending greetings {}", greeting);
		Executors.newSingleThreadExecutor().submit(() -> {
			MessageChannel messageChannel = discountStream.outboundDiscountStream();
			messageChannel.send(MessageBuilder.withPayload(auditEventPayload)
					.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
		});

	}

}
