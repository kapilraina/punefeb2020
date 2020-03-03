package com.ms.bootcamp.DiscountMicroservice.metrics;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ms.bootcamp.DiscountMicroservice.DiscountResponse;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@Service
public class DiscountMetricsService {
	private static Logger log = LoggerFactory.getLogger(DiscountMetricsService.class);

	/*
	 * @Bean MeterRegistryCustomizer<MeterRegistry> registryCustomizer(
	 * 
	 * @Value("${DISCOUNTSERVICE:_DISCOUNTMICROSERVICE}") String serviceName) {
	 * return registry -> registry.config().commonTags("microservice", serviceName);
	 * }
	 */

	

	private MeterRegistry meterRegistry;

	public DiscountMetricsService(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}

	@Async
	public void createAndLogMetrics(DiscountResponse response) {

		try {

			AtomicLong discount = new AtomicLong(0);
			String gaugeName = "discountms.categorydiscountmetric";
			Gauge discountGauge = Gauge.builder(gaugeName, discount, f -> f.get())
					.tag("category", response.getCategory().name()).register(meterRegistry);

			/*
			 * discount = meterRegistry.gauge("discountms.discountmetric." +
			 * response.getCategory().name(), discount, f -> f.get());
			 */

			discount.set((long) (response.getMrp() - response.getDrp()));
			log.info("\n**** Gauge Logging :" + response.getCategory().name() + " : " + discount);

			log.info("\n**** Gauging :" + discountGauge.value());

			String counterName = "discountms.categorydiscountcounter";
			Counter discountCounter = Counter.builder(counterName).tag("category", response.getCategory().name())
					.register(meterRegistry);
			discountCounter.increment();

		} catch (Throwable t) {
			log.error("\n**** Discount MS Gauge Logging Error = " + t);
		}

	}

}
