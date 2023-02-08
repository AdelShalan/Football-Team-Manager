package com.teamanager.contract.service.service;

import com.teamanager.contract.service.VO.Player;
import com.teamanager.contract.service.VO.Position;
import com.teamanager.contract.service.exception.PlayerNotFoundException;
import com.teamanager.contract.service.exception.RestCallException;
import com.teamanager.contract.service.exception.TeamNotFoundException;
import com.teamanager.contract.service.model.Contract;
import com.teamanager.contract.service.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public Long getPlayerTeamId(Long playerId) {
        Contract result = contractRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(
                        String.format("Player with id: %s could not be found in contract repository!", playerId)));
        return result.getTeamId();
    }

    public Set<Player> getTeamPlayers(Long teamId) {
        List<Contract> teamContracts = contractRepository.findByTeamId(teamId)
                .orElseThrow(() -> new TeamNotFoundException(String.format("No contracts found for team id: %s", teamId)));
        Set<Player> players = new HashSet<>();
        for (Contract c : teamContracts) {
            try {
                players.add(restTemplate.getForObject("http://PLAYER-SERVICE/api/v1/player/" + c.getPlayerId(),
                        Player.class));
            } catch (Exception e) {
                players.add(new Player(c.getPlayerId() == null ? 0L : c.getPlayerId(), "UNKNOWN", 0, Position.NA));
                throw new RestCallException(e.getMessage());
            }
        }

        return players;
    }
}
