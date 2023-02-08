package com.teamanager.playerservice.controller;

import com.teamanager.playerservice.VO.Contract;
import com.teamanager.playerservice.model.Offer;
import com.teamanager.playerservice.repository.OfferRepository;
import com.teamanager.playerservice.service.OfferService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/player/offer/")
@Slf4j(topic = "OFFER_CONTROLLER")
public class OfferController {

    @Autowired
    OfferService offerService;

    @ApiOperation("View all offers for a player")
    @GetMapping("/viewAll")
    public List<Offer> findPlayerById(@RequestParam("playerId") Long playerId) {
        return offerService.viewOffers(playerId);
    }

    @ApiOperation("Accept an offer")
    @PostMapping("/acceptOffer/{offerId}")
    public Contract acceptOffer(@PathVariable("offerId") Long offerId) {
        return offerService.acceptOffer(offerId);
    }
}
