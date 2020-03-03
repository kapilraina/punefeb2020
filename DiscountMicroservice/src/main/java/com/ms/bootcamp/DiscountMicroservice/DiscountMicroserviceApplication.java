package com.ms.bootcamp.DiscountMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@SpringBootApplication

public class DiscountMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountMicroserviceApplication.class, args);
	}

	@Bean
	public MeterRegistry initRegistry() {
		CompositeMeterRegistry meterRegistry = new CompositeMeterRegistry();
		JmxMeterRegistry jmxregistry = new JmxMeterRegistry(new JmxConfig() {
			@Override
			public String get(String s) {
				return null;
			}
		}, Clock.SYSTEM);
		meterRegistry.add(jmxregistry);

		PrometheusMeterRegistry prommeterRegistry = new PrometheusMeterRegistry(new PrometheusConfig() {

			@Override
			public String get(String key) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		meterRegistry.add(prommeterRegistry);

		return meterRegistry;
	}
}
