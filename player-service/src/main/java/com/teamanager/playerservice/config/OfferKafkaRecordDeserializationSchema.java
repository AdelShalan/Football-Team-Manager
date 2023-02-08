package com.teamanager.playerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamanager.playerservice.model.Offer;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class OfferKafkaRecordDeserializationSchema implements KafkaDeserializationSchema<Offer> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TypeInformation<Offer> getProducedType() {
        return TypeInformation.of(Offer.class);
    }

    @Override
    public boolean isEndOfStream(Offer offer) {
        return false;
    }

    @Override
    public Offer deserialize(ConsumerRecord<byte[], byte[]> consumerRecord) throws Exception {
        return objectMapper.readValue(consumerRecord.value(), Offer.class);
    }
}
