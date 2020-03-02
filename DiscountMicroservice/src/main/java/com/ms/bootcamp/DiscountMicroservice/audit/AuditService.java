package com.ms.bootcamp.DiscountMicroservice.audit;

import java.util.concurrent.Executors;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class AuditService {

	private final AuditStream auditStream;

	public AuditService(AuditStream auditStream) {
		this.auditStream = auditStream;
	}

	public void pubAuditEvent(final String auditEventPayload) {
		// log.info("Sending greetings {}", greeting);
		Executors.newSingleThreadExecutor().submit(() -> {
			MessageChannel messageChannel = auditStream.outboundAudit();
			messageChannel.send(MessageBuilder.withPayload(auditEventPayload)
					.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN_VALUE).build());
		});

	}

}
