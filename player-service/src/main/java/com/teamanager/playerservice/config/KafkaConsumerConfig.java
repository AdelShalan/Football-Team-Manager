package com.teamanager.playerservice.config;

import com.teamanager.playerservice.model.Offer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Bean
    public ConsumerFactory<String, Offer> offerConsumer() {
        JsonDeserializer<Offer> deserializer = new JsonDeserializer<>(Offer.class, false);

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "player-service-offers-listener");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Offer> offersListener() {
        ConcurrentKafkaListenerContainerFactory<String, Offer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(offerConsumer());
        return factory;
    }

}
