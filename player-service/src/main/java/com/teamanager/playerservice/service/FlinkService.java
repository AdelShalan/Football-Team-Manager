package com.teamanager.playerservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamanager.playerservice.config.KafkaSourceConfig;
import com.teamanager.playerservice.config.OfferKafkaRecordDeserializationSchema;
import com.teamanager.playerservice.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.ZoneId;
import java.util.Properties;

@Service
@Slf4j(topic = "FLINK_SERVICE")
public class FlinkService {
    private static StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    private static Properties properties = new Properties();
    private DataStream<Offer> input;

    public FlinkService() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "flink-service-offers-listener");
        FlinkKafkaConsumer<Offer> consumer = new FlinkKafkaConsumer<>(
                "offers",
                new OfferKafkaRecordDeserializationSchema(),
                properties
        );
        env.setParallelism(2); //set 2 asynch task managers
        input = env.addSource(consumer);
        logStream(processStream());
        //input.print();
        //env.execute("Test");
    }

    public DataStream<Offer> processStream() {
        return input.filter(offer -> {
            Month month = offer.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth();
            return month == Month.JANUARY || month == Month.JUNE;
                });
    }

    public void logStream(DataStream input) {
        input.print();
    }
}
