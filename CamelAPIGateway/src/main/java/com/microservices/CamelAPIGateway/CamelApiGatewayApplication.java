package com.microservices.CamelAPIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CamelApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelApiGatewayApplication.class, args);
	}

	
	/*
	 * @Bean public BridgePropertyPlaceholderConfigurer propsBridge() { final
	 * YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean(); final
	 * BridgePropertyPlaceholderConfigurer result = new
	 * BridgePropertyPlaceholderConfigurer();
	 * 
	 * yaml.setResources( new ClassPathResource( "application.yaml" ) );
	 * result.setOrder( 1 ); result.setIgnoreUnresolvablePlaceholders( true );
	 * result.setProperties( yaml.getObject() );
	 * 
	 * return result; }
	 */
	 

	/*
	 * @Autowired BridgePropertyPlaceholderConfigurer
	 * bridgePropertyPlaceholderConfigurer;
	 */

}
