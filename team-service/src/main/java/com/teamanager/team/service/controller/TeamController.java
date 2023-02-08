package com.teamanager.team.service.controller;

import com.teamanager.team.service.VO.Manager;
import com.teamanager.team.service.VO.Player;
import com.teamanager.team.service.VO.ResponseTemplate;
import com.teamanager.team.service.model.Team;
import com.teamanager.team.service.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/create")
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @GetMapping("/{teamId}")
    public Team getTeam(@PathVariable("teamId") Long teamId) throws Exception {
        return teamService.findTeamById(teamId);
    }

    @GetMapping("/{teamId}/info")
    public ResponseTemplate viewTeamInfo(@PathVariable("teamId") Long teamId) throws Exception {
        return teamService.viewTeamInfo(teamId);
    }

    @GetMapping("/{teamId}/players")
    public Set<Player> getTeamPlayers(@PathVariable("teamId") Long teamId) throws Exception {
        return teamService.getTeamPlayers(teamId);
    }

    @GetMapping("/{teamId}/manager")
    public Manager getTeamManager(@PathVariable("teamId") Long teamId) throws Exception {
        return teamService.getTeamManager(teamId);
    }

    @PutMapping("/{teamId}/assignManager/{managerId}")
    public Team assignTeamManager(@PathVariable("teamId") Long teamId, @PathVariable("managerId") Long managerId) throws Exception {
        return teamService.assignTeamManager(teamId, managerId);
    }

}
