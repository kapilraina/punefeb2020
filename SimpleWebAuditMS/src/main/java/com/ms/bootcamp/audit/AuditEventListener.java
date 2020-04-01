package com.ms.bootcamp.audit;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {
	@Autowired
	AuditEventRepository repo;
	private static Logger log = LoggerFactory.getLogger(AuditEventListener.class);

	@StreamListener(AuditStream.INPUT)
	public void handleAudit(@Payload String auditPayload) {

		log.info("Received =" + auditPayload);
		AuditEvent event = new AuditEvent(UUID.randomUUID().toString(), auditPayload,new Date());
		repo.save(event);
	}
	/*
	 * @StreamListener(AuditStream.INPUT) public void
	 * handleAuditwithAck(Message<String> message) { String msg =
	 * message.getPayload(); System.out.println("Message Payload Received = " +
	 * msg); Acknowledgment acknowledgment =
	 * message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
	 * if (acknowledgment != null) { System.out.println("Acknowledgment provided");
	 * acknowledgment.acknowledge(); } }
	 */

}
