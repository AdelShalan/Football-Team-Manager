package com.teamanager.playerservice.controller;

import com.teamanager.playerservice.VO.Contract;
import com.teamanager.playerservice.model.Player;
import com.teamanager.playerservice.service.PlayerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/player")
@Slf4j(topic = "PLAYER_CONTROLLER")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @ApiOperation("Create a new player")
    @PostMapping("/create")
    public Player createPlayer(@RequestBody Player player) {
        return playerService.save(player);
    }

    @ApiOperation("Get a player")
    @GetMapping("/{playerId}")
    public Player findPlayerById(@PathVariable("playerId") Long playerId) {
        return playerService.findPlayerById(playerId);
    }

    public Contract joinTeamFallBack(@PathVariable("playerId") Long playerId, @PathVariable("teamId") Long teamId, Exception e){
        log.error(e.getMessage());
        return new Contract();
    }

}
