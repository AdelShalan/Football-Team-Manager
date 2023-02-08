package com.teamanager.manager.service.service;

import com.teamanager.manager.service.VO.Offer;
import com.teamanager.manager.service.repository.ManagerRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {com.teamanager.manager.service.service.ManagerService.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9091", "port=9091"})
public class ManagerServiceKafkaTest {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private KafkaTemplate<String, Offer> kafkaTemplate;

    @Test
    public void sendOffer_validOffer_offerSent() throws InterruptedException {
        Long managerId = 1L;
        Offer offer = new Offer(1, 1, 2, new Date());
        managerService.sendOffer(managerId, offer);
        ConsumerRecord<String, Offer> consumerRecord = new ConsumerRecord<>(
                "offers", 1, 1L, "key", offer);
        EmbeddedKafkaBroker embeddedKafkaBroker = new EmbeddedKafkaBroker(1);
        // TO-DO: how to test the kafka producer without defining a consumer?
        /*ConsumerRecord<String, Offer> record =;
        assertNotNull(record);*/
        //assertEquals(offer, record.value());

    }

    @Test(expected = RuntimeException.class)
    public void sendOffer_offerNotForManagersTeam_exceptionThrown() {
        Long managerId = 2L;
        when(managerService.getManagerTeam(managerId).getId()).thenReturn(1L);
        Offer offer = new Offer(1, 1, 2, new Date());
        managerService.sendOffer(managerId, offer);
    }

}
