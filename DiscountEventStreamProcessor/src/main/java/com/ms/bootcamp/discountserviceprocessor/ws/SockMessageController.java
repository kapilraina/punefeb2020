package com.ms.bootcamp.discountserviceprocessor.ws;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class SockMessageController {

	@MessageMapping("/register")
	public void register(SimpMessageHeaderAccessor headerAccessor, @Header("simpSessionId") String sessionId) {

		System.out.println("NEW SESSION :" + "{sessionid :" + sessionId + "}");

	}

	@MessageMapping("/unregister")
	public void unregister(@Header("simpSessionId") String sessionId) {
		System.out.println("EXITED SESSION :" + "{sessionid :" + sessionId + "}");
	}

}
