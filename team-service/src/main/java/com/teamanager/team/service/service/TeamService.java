package com.teamanager.team.service.service;

import com.teamanager.team.service.VO.Manager;
import com.teamanager.team.service.VO.Player;
import com.teamanager.team.service.VO.ResponseTemplate;
import com.teamanager.team.service.exception.HasManagerException;
import com.teamanager.team.service.exception.ManagerNotFoundException;
import com.teamanager.team.service.exception.RestCallException;
import com.teamanager.team.service.exception.TeamNotFoundException;
import com.teamanager.team.service.model.Team;
import com.teamanager.team.service.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
@Slf4j
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Can't find team with id: " + teamId));
    }

    public ResponseTemplate viewTeamInfo(Long teamId) {
        Team team = findTeamById(teamId);
        Manager manager = getTeamManager(teamId);
        Set<Player> players = getTeamPlayers(teamId);
        return new ResponseTemplate(team, manager, players);
    }

    public Manager getTeamManager(Long teamId) {
        Team team = findTeamById(teamId);
        if (team.getManagerId() == null)
            throw new ManagerNotFoundException("Can't find manager for team id: " + teamId);
        try {
            return restTemplate.getForObject("http://MANAGER-SERVICE/api/v1/manager/" + team.getManagerId(),
                    Manager.class);
        } catch (Exception e) {
            throw new RestCallException(e.getMessage());
        }
    }

    public Team assignTeamManager(Long teamId, Long managerId) throws Exception {
        Team team = findTeamById(teamId);
        if (team.getManagerId() != null) {
            throw new HasManagerException("Team (id: " + teamId + ") already has a manager!");
        } else {
            team.setManagerId(managerId);
            return teamRepository.save(team);
        }
    }

    public Set<Player> getTeamPlayers(Long teamId) {
        try {
            return restTemplate.getForObject("http://CONTRACT-SERVICE/api/v1/contract/teamPlayers/" + teamId,
                    Set.class);
        } catch (Exception e) {
            throw new RestCallException(e.getMessage());
        }
    }

}
