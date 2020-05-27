package com.ms.bootcamp.discountserviceprocessor.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		/**
		 * we enable an in-memory message broker to carry the messages back to the
		 * client on destinations prefixed with “/topic”.
		 */
		registry.enableSimpleBroker("/topic");
		/**
		 * We complete our simple configuration by designating the “/app” prefix to
		 * filter destinations targeting application annotated methods
		 * (via @MessageMapping).
		 */
		registry.setApplicationDestinationPrefixes("/app");
		WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		/**
		 * The registerStompEndpoints method registers the “/websock” endpoint, enabling
		 * Spring’s STOMP support. Keep in mind that we are also adding here an endpoint
		 * that works without the SockJS for the sake of elasticity.
		 */
		registry.addEndpoint("/websock");
		/**
		 * The fallbacks let the applications use a WebSocket API but gracefully degrade
		 * to non-WebSocket alternatives when necessary at runtime.s
		 */
		registry.addEndpoint("/websock").withSockJS();

		WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
	}
}
