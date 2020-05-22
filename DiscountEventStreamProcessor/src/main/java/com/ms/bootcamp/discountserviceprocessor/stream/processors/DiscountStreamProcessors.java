package com.ms.bootcamp.discountserviceprocessor.stream.processors;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindowedKStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscountStreamProcessors {
	/*
	 * @Bean public Consumer<KStream<String, DiscountResponse>>
	 * simpleDiscountPipeline() { return kstream -> { kstream.peek((k, v) ->
	 * System.out.println(k + " : " + v)); }; }
	 */

	@Bean
	public Function<KStream<String, DiscountResponse>, KStream<String, Double>> winaggdisc() {

		return kstream -> {

			KGroupedStream<String, DiscountResponse> kGroupedStream = kstream.groupByKey();

			TimeWindowedKStream<String, DiscountResponse> timeWindowedKStream = kGroupedStream
					.windowedBy(TimeWindows.of(Duration.ofSeconds(1000)));

			KTable<Windowed<String>, Double> kAggDiscountTable = timeWindowedKStream.aggregate(

					() -> 0.0, (aggKey, newValue, aggValue) -> aggValue + (newValue.getMrp() - newValue.getDrp()),

					Materialized.<String, Double, WindowStore<Bytes, byte[]>>as("TX_WINDOWED_AGG_DISCOUNTSTREAM_STORE01")
							.withValueSerde(Serdes.Double()).withKeySerde(Serdes.String()));

			return kAggDiscountTable.toStream().map((k, v) -> KeyValue.pair(k.key(), v));

		};
	}

}