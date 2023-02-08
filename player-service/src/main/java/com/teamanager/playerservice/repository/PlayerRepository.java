package com.teamanager.playerservice.repository;

import com.teamanager.playerservice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
