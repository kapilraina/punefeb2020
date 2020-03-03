package com.ms.bootcamp.DiscountMicroservice.audit;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.bootcamp.DiscountMicroservice.DiscountController;
import com.ms.bootcamp.DiscountMicroservice.DiscountResponse;

@Service
public class AuditService {

	private final AuditStream auditStream;
	private static Logger log = LoggerFactory.getLogger(AuditService.class);

	public AuditService(AuditStream auditStream) {
		this.auditStream = auditStream;
	}

	@Async
	public void pubAuditEvent(final DiscountResponse response) {

		String responseJSON = "";
		try {
			ObjectMapper obj = new ObjectMapper();
			responseJSON = obj.writeValueAsString(response);
			MessageChannel messageChannel = auditStream.outboundAudit();
			messageChannel.send(MessageBuilder.withPayload(responseJSON)
					.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN_VALUE).build());

		} catch (Exception e) {
			log.error("Couldnt Emit Discount Stream. Error :" + e.getMessage());
			log.info(responseJSON);
		}

	}

}
