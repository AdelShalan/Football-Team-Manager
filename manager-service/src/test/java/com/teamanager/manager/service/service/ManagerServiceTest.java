package com.teamanager.manager.service.service;

import com.teamanager.manager.service.VO.Team;
import com.teamanager.manager.service.exception.HasTeamException;
import com.teamanager.manager.service.exception.RestCallException;
import com.teamanager.manager.service.model.Manager;
import com.teamanager.manager.service.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Transactional
class ManagerServiceTest {
    @InjectMocks
    private ManagerService managerService;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void assignManagerTeam_managerHasTeam_throwsHasTeamException() {
        Manager manager = new Manager();
        manager.setTeamId(1L);
        when(managerRepository.findById(1L)).thenReturn(java.util.Optional.of(manager));
        try {
            managerService.assignManagerTeam(1L, 2L);
        } catch (HasTeamException e) {
            assertEquals("Manager already has a team!", e.getMessage());
        }
        verify(managerRepository, times(0)).save(manager);
    }

    @Test
    public void assignManagerTeam_successfulAssignment_managerAndTeamAssigned() {
        Manager manager = new Manager();
        manager.setTeamId(0L);
        when(managerRepository.findById(1L)).thenReturn(java.util.Optional.of(manager));
        managerService.assignManagerTeam(1L, 2L);
        verify(managerRepository, times(1)).save(manager);
        verify(restTemplate, times(1)).put(eq("http://TEAM-SERVICE/api/v1/team/2/assignManager/1"), eq(Team.class));
    }

    @Test
    public void assignManagerTeam_restCallException_rollbackTransaction() {
        // Given
        Long managerId = 1L;
        Long teamId = 2L;

        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setTeamId(0L);

        when(managerRepository.findById(managerId)).thenReturn(Optional.of(manager));
        doThrow(new RuntimeException("error")).when(restTemplate).put("http://TEAM-SERVICE/api/v1/team/2/assignManager/1", Team.class);
        // When
        try {
            managerService.assignManagerTeam(managerId, teamId);
            fail("Expected RestCallException");
        } catch (RestCallException e) {
            // Then
            //verify(managerRepository, never()).save(manager);
        }
        manager = managerService.findManagerById(managerId);
        assertEquals(manager.getTeamId(), 0L);
    }

}
