package com.teamanager.manager.service.controller;

import com.teamanager.manager.service.VO.Offer;
import com.teamanager.manager.service.VO.Team;
import com.teamanager.manager.service.model.Manager;
import com.teamanager.manager.service.service.ManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager")
@Slf4j
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @ApiOperation("Create a new manager")
    @PostMapping("/create")
    public Manager createManager(@RequestBody Manager manager){
        return managerService.create(manager);
    }

    @ApiOperation("Send offer")
    @PostMapping("/{managerId}/sendOffer")
    public void sendOffer(@PathVariable Long managerId, @RequestBody Offer offer){
        managerService.sendOffer(managerId, offer);
    }

    @ApiOperation("Find a manager")
    @GetMapping("/{managerId}")
    public Manager getManagerById(@PathVariable("managerId") Long managerId) throws Exception {
        return managerService.findManagerById(managerId);
    }

    @ApiOperation("Find a manager's team")
    @GetMapping("/{managerId}/team")
    public Team getManagerTeam(@PathVariable("managerId") Long managerId) throws Exception {
        return managerService.getManagerTeam(managerId);
    }

    @ApiOperation("Assign manager to a team")
    @PutMapping("/{managerId}/assignteam/{teamId}")
    public void assignManagerTeam(@PathVariable("managerId") Long managerId, @PathVariable("teamId") Long teamId) throws Exception {
        managerService.assignManagerTeam(managerId,teamId);
    }
}