package com.ms.bootcamp.discountserviceprocessor.ws;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ms.bootcamp.discountserviceprocessor.stream.processors.AggregatedWindowedDiscount;

@Service
public class DiscountSocketPushService {

	@Autowired
	private SimpMessagingTemplate template;
	private SimpleDateFormat sdf = new SimpleDateFormat("d/MMM/yyyy HH:mm:ss");
	private DecimalFormat df = new DecimalFormat("0.00");

	public void pipeToWebSocket(AggregatedWindowedDiscount awd) {
		System.out.println("Pipping :" + awd);
		AggregatedWindowedDiscountSocksPayload awdsp = new AggregatedWindowedDiscountSocksPayload();
		awdsp.setCategory(awd.getCategory());
		awdsp.setWindowTotal(df.format(awd.getWindowTotal()));
		awdsp.setWindowStart(sdf.format(awd.getWindowStart()));
		awdsp.setWindowEnd(sdf.format(awd.getWindowEnd()));
		template.convertAndSend("/topic/messages", awdsp);
	}

}
