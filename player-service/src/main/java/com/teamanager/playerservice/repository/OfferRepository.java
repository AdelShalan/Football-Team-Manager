package com.teamanager.playerservice.repository;

import com.teamanager.playerservice.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Optional<List<Offer>> findByPlayerId(Long playerId);
}
