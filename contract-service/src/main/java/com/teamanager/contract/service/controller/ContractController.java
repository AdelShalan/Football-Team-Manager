package com.teamanager.contract.service.controller;

import com.teamanager.contract.service.VO.Player;
import com.teamanager.contract.service.model.Contract;
import com.teamanager.contract.service.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/create")
    public Contract createContract(@RequestBody Contract contract) {
        return contractService.createContract(contract);
    }

    @GetMapping("/playerTeam/{playerId}")
    public Long getPlayerTeamId(@PathVariable("playerId") Long playerId) {
        return contractService.getPlayerTeamId(playerId);
    }

    @GetMapping("/teamPlayers/{teamId}")
    public Set<Player> getTeamPlayersIds(@PathVariable("teamId") Long teamId) {
        return contractService.getTeamPlayers(teamId);
    }
}
