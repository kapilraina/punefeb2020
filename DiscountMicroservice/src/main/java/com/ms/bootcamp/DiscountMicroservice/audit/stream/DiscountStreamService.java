package com.ms.bootcamp.DiscountMicroservice.audit.stream;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.bootcamp.DiscountMicroservice.DiscountResponse;

@Service
public class DiscountStreamService {

	private final DiscountStream discountStream;
	private static Logger log = LoggerFactory.getLogger(DiscountStreamService.class);

	public DiscountStreamService(DiscountStream discountStream) {
		this.discountStream = discountStream;
	}

	@Async
	public void pubAuditEvent(final DiscountResponse response) {
		// log.info("Sending greetings {}", greeting);

		String responseJSON = "";
		ObjectMapper obj = new ObjectMapper();
		try {
			responseJSON = obj.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			log.error("Couldnt Emit Discount Audit Event/Stream. Error :" + e.getMessage());

		}
		MessageChannel messageChannel = discountStream.outboundDiscountStream();
		messageChannel.send(MessageBuilder.withPayload(responseJSON)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());

	}

}
