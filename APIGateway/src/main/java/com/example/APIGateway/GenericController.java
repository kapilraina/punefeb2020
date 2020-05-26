package com.example.APIGateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericController {
	@GetMapping("/greetingfallback")
	public ResponseEntity<String> booksFallback(Exception e) {
		return ResponseEntity.ok("Default Greeting "+e.getLocalizedMessage()); 
	}

	@ExceptionHandler(value = Exception.class)
	public String exceptionHandler(Exception e) {
		return "ERROR: " + e.getMessage() + " | " + e.getCause();
	}

	/*
	 * @GetMapping("/") public String index(Model
	 * model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient
	 * authorizedClient,
	 * 
	 * @AuthenticationPrincipal OAuth2User oauth2User) {
	 * model.addAttribute("userName", oauth2User.getName());
	 * model.addAttribute("clientName",
	 * authorizedClient.getClientRegistration().getClientName());
	 * model.addAttribute("userAttributes", oauth2User.getAttributes()); return
	 * "index"; }
	 */
}
