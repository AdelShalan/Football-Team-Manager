package com.teamanager.playerservice.config;

import com.teamanager.playerservice.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
@Slf4j(topic = "KAFKA_SOURCE_CONFIG")
public class KafkaSourceConfig implements SourceFunction<Offer> {
    private final Properties properties;
    private final String topic;
    private volatile boolean isRunning = true;

    public KafkaSourceConfig(Properties properties, String topic) {
        this.properties = properties;
        this.topic = topic;
    }

    @Override
    public void run(SourceContext<Offer> sourceContext) throws Exception {
        KafkaConsumer<String, Offer> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topic));

        while (isRunning) {
            ConsumerRecords<String, Offer> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Offer> record : records) {
                sourceContext.collect(record.value());
            }
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
