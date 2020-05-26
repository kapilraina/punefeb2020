package com.ms.bootcamp.productmicroservice.service;





import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;

//@Configuration
public class RibbonConfiguration {

	/*
	 * @Autowired
	 * 
	 * @Lazy IClientConfig ribbonClientConfig;
	 * 
	 * @Autowired //@Lazy ILoadBalancer iLoadBalancer;
	 */

	@Bean
	//public IPing ribbonPing(IClientConfig config) {
	public IPing ribbonPing() {
		return new PingUrl();
	}

	@Bean
	//public IRule ribbonRule(IClientConfig config) {
	public IRule ribbonRule() {

		//return new RoundRobinRule();
		 return new AvailabilityFilteringRule();
	}

}