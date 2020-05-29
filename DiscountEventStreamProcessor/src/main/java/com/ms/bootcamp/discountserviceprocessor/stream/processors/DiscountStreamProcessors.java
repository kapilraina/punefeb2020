package com.ms.bootcamp.discountserviceprocessor.stream.processors;

import java.time.Duration;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindowedKStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ms.bootcamp.discountserviceprocessor.ws.DiscountSocketPushService;

@Configuration
public class DiscountStreamProcessors {
	/*
	 * @Bean public Consumer<KStream<String, DiscountResponse>>
	 * simpleDiscountPipeline() { return kstream -> { kstream.peek((k, v) ->
	 * System.out.println(k + " : " + v)); }; }
	 */

	@Autowired
	private DiscountSocketPushService dsps;

	@Bean
	public Function<KStream<String, DiscountResponse>, KStream<String, AggregatedWindowedDiscount>> winaggdisc() {

		return kstream -> {

			KGroupedStream<String, DiscountResponse> kGroupedStream = kstream.groupByKey();

			TimeWindowedKStream<String, DiscountResponse> timeWindowedKStream = kGroupedStream
					.windowedBy(TimeWindows.of(Duration.ofSeconds(3600)));

			KTable<Windowed<String>, Double> kAggDiscountTable = timeWindowedKStream.aggregate(

					() -> 0.0, (aggKey, newValue, aggValue) -> aggValue + (newValue.getMrp() - newValue.getDrp()),

					Materialized
							.<String, Double, WindowStore<Bytes, byte[]>>as("TX_WINDOWED_AGG_DISCOUNTSTREAM_STORE01")
							.withValueSerde(Serdes.Double()).withKeySerde(Serdes.String()));

			return kAggDiscountTable.toStream()
					.map((k, v) -> KeyValue.pair(k.key(), new AggregatedWindowedDiscount(k.key(), v,
							new Date(k.window().start()), new Date(k.window().end()))))
					.peek((k, v) -> dsps.pipeToWebAggSocket(v));

		};
	}

	@Bean
	public Consumer<KStream<String, DiscountResponse>> windiscbyinstance() {

		return kstream -> {

			kstream.peek((k, v) -> {
				WindowedDiscountByInstance bbi = new WindowedDiscountByInstance();

				bbi.setCategory(k);
				bbi.setDiscountApplied(v.getFixedCategoryDiscount() + v.getOnSpotDiscount());
				bbi.setTimestamp(v.getTimestamp());
				dsps.pipeToWebInstanceSocket(bbi);

			});
		};

	}

}
