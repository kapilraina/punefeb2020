package com.ms.bootcamp.DiscountMicroservice.metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ms.bootcamp.DiscountMicroservice.DiscountResponse;
import com.ms.bootcamp.DiscountMicroservice.ProductCategory;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

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

	/**
	 * It is your responsibility to hold a strong reference to the state object that
	 * you are measuring with a Gauge. Micrometer is careful to not create strong
	 * references to objects that would otherwise be garbage collected. Once the
	 * object being gauged is de-referenced and is garbage collected, Micrometer
	 * will start reporting a NaN or nothing for a gauge, depending on the registry
	 * implementation.
	 */
	private MeterRegistry meterRegistry;
	private Map<ProductCategory, AtomicLong> gaugeMap;

	public DiscountMetricsService(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
		gaugeMap = new HashMap<ProductCategory, AtomicLong>();
		String gaugeName = "discountms.categorydiscountmetric";
		for (ProductCategory pc : ProductCategory.values()) {
			AtomicLong discount = new AtomicLong(0);

			Tag t = new Tag() {

				@Override
				public String getValue() {
					// TODO Auto-generated method stub
					return pc.name();
				}

				@Override
				public String getKey() {
					// TODO Auto-generated method stub
					return "category";
				}
			};

			List<Tag> tags = new ArrayList<Tag>();
			tags.add(t);
			discount = meterRegistry.gauge(gaugeName, tags, discount, f -> f.get());

			gaugeMap.put(pc, discount);

		}

		/*
		 * Gauge discountGauge = Gauge.builder(gaugeName, discount, f -> f.get())
		 * .tag("category", response.getCategory().name()).register(meterRegistry);
		 */

	}

	@Async
	public void createAndLogMetrics(DiscountResponse response) {

		try {
			gaugeMap.get(response.getCategory())
					.set((long) (response.getOnSpotDiscount() + response.getFixedCategoryDiscount()));

			log.info("\n**** Gauge Logging :" + response.getCategory().name() + " : "
					+ gaugeMap.get(response.getCategory()));

			String counterName = "discountms.categorydiscountcounter";
			Counter discountCounter = Counter.builder(counterName).tag("category", response.getCategory().name())
					.register(meterRegistry);
			discountCounter.increment();

		} catch (Throwable t) {
			log.error("\n**** Discount MS Gauge Logging Error = " + t);
		}

	}

}
