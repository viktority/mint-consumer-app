package com.viktority.mint.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.viktority.mint.models.Payload;



@Configuration
@EnableKafka
public class KafkaConfig {

	@Autowired
	private Environment env;

	@Bean
	public ConsumerFactory<String, Payload> consumerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.server.config.url"));
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("kafka.server.group.id"));

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(Payload.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Payload> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Payload> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();

		concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
		return concurrentKafkaListenerContainerFactory;
	}

}
