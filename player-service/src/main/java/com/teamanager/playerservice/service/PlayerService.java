package com.teamanager.playerservice.service;

import com.teamanager.playerservice.VO.Contract;
import com.teamanager.playerservice.exception.HasTeamException;
import com.teamanager.playerservice.exception.PlayerNotFoundException;
import com.teamanager.playerservice.exception.RestCallException;
import com.teamanager.playerservice.model.Player;
import com.teamanager.playerservice.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j(topic = "PLAYER_SERVICE")
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player findPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("can not find player with id " + playerId));
    }

    @Transactional(rollbackFor = {RestCallException.class})
    public Contract joinTeam(Long playerId, Long teamId) {
        Player player = findPlayerById(playerId);

        if (player.getTeamId() != null)
            throw new HasTeamException("Player already has a team!");
        else {
            player.setTeamId(teamId);
            playerRepository.save(player);
            Contract contract = new Contract(playerId, teamId);
            try {
                return restTemplate.postForObject(
                                "http://CONTRACT-SERVICE/api/v1/contract/create",
                                contract,
                                Contract.class);
            } catch (Exception e) {
                throw new RestCallException(e.getMessage());
            }
        }
    }
}