package com.teamanager.playerservice.service;

import com.teamanager.playerservice.VO.Contract;
import com.teamanager.playerservice.exception.OfferNotFoundException;
import com.teamanager.playerservice.model.Offer;
import com.teamanager.playerservice.repository.OfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j(topic = "OFFER_SERVICE")
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private PlayerService playerService;

    public OfferService() {
        log.info("Initialized offer service!!");
    }

    @KafkaListener(topics = "offers", groupId = "player-service-offers-listener", containerFactory = "offersListener")
    public void saveOffers(@Payload Offer offer){
        offerRepository.save(offer);
        log.info(String.format("saved %s", offer));
    }

    public List<Offer> viewOffers(Long playerId) {
        return offerRepository.findByPlayerId(playerId).orElseThrow(() ->
                new OfferNotFoundException(String.format("No offers found for playerId: %s!", playerId)));
    }

    public Contract acceptOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new OfferNotFoundException("No such offer"));
        return playerService.joinTeam(offer.getPlayerId(), offer.getTeamId());
    }
}
