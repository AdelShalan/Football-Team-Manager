package com.teamanager.manager.service.service;

import com.teamanager.manager.service.VO.Offer;
import com.teamanager.manager.service.VO.Team;
import com.teamanager.manager.service.exception.HasTeamException;
import com.teamanager.manager.service.exception.ManagerNotFoundException;
import com.teamanager.manager.service.exception.RestCallException;
import com.teamanager.manager.service.model.Manager;
import com.teamanager.manager.service.repository.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private KafkaTemplate<String, Offer> kafkaTemplate;

    public Manager create(Manager manager) {
        return managerRepository.save(manager);
    }

    public Manager findManagerById(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new ManagerNotFoundException("Can not find manager with id " + managerId));
    }

    @Transactional(rollbackFor = RestCallException.class)
    public void assignManagerTeam(Long managerId, Long teamId) {
        Manager manager = findManagerById(managerId);
        if (manager.getTeamId().equals(0L)) {
            manager.setTeamId(teamId);
            managerRepository.save(manager);
            try {
                restTemplate.put("http://TEAM-SERVICE/api/v1/team/" + teamId + "/assignManager/" + managerId, Team.class);
            } catch (Exception e) {
                throw new RestCallException(e.getMessage());
            }
        } else {
            throw new HasTeamException("Manager already has a team!");
        }
    }

    public Team getManagerTeam(Long managerId) {
        Manager manager = findManagerById(managerId);
        return restTemplate.getForObject("http://TEAM-SERVICE/api/v1/team/" + manager.getTeamId(), Team.class);
    }

    //Sends a kafka offer event to the "offers" stream if Manager is eligible to send the offer
    public void sendOffer(Long managerId, Offer offer) {
        if (offer.getTeamId() == getManagerTeam(managerId).getId())
            kafkaTemplate.send("offers", offer);
        else throw new RuntimeException("Manager does not manage the team with ID: " + offer.getTeamId());
    }

}