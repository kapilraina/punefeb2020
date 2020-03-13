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

	
	public void pubAuditEventStream(final DiscountResponse response) {
		log.info(">>pubAuditEventStream Aync");
		Executors.newFixedThreadPool(1).execute(() -> {
			String responseJSON = "";
			ObjectMapper obj = new ObjectMapper();
			try {
				Thread.sleep(2000);
				responseJSON = obj.writeValueAsString(response);
				MessageChannel messageChannel = discountStream.outboundDiscountStream();
				messageChannel.send(MessageBuilder.withPayload(responseJSON)
						.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
			} catch (Throwable e) {
				log.error("\nCouldnt Emit Discount Audit Event/Stream. Error :" + e.getMessage());
				log.info("\nLogging Discount Response as fallback: " + response);

			} 

		});

		
	}

}
