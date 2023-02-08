package com.teamanager.contract.service.service;

import com.teamanager.contract.service.VO.Player;
import com.teamanager.contract.service.VO.Position;
import com.teamanager.contract.service.exception.PlayerNotFoundException;
import com.teamanager.contract.service.exception.TeamNotFoundException;
import com.teamanager.contract.service.model.Contract;
import com.teamanager.contract.service.repository.ContractRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ContractServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ContractService contractService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createContract() {
    }

    @Test
    public void getPlayerTeamId() {
        // Arrange
        Long playerId = 1L;
        Long expectedTeamId = 2L;
        Contract contract = new Contract();
        contract.setTeamId(expectedTeamId);
        when(contractRepository.findByPlayerId(playerId)).thenReturn(Optional.of(contract));

        // Act
        Long actualTeamId = contractService.getPlayerTeamId(playerId);

        // Assert
        assertEquals(expectedTeamId, actualTeamId);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void testGetPlayerTeamId_playerNotFound() {
        // Arrange
        Long playerId = 1L;
        when(contractRepository.findByPlayerId(playerId)).thenReturn(Optional.empty());
        // Act
        contractService.getPlayerTeamId(playerId);
    }

    @Test
    public void testGetTeamPlayers() {
        Long teamId = 1L;
        Contract contract1 = new Contract(1L, 1L, teamId);
        Contract contract2 = new Contract(2L, 2L, teamId);
        when(contractRepository.findByTeamId(teamId)).thenReturn(Optional.of(Arrays.asList(contract1, contract2)));
        Player player1 = new Player(1L, "Player 1", 25, Position.ST);
        Player player2 = new Player(2L, "Player 2", 30, Position.DF);
        when(restTemplate.getForObject("http://PLAYER-SERVICE/api/v1/player/1", Player.class)).thenReturn(player1);
        when(restTemplate.getForObject("http://PLAYER-SERVICE/api/v1/player/2", Player.class)).thenReturn(player2);
        Set<Player> expectedPlayers = new HashSet<>(Arrays.asList(player1, player2));
        assertEquals(expectedPlayers, contractService.getTeamPlayers(teamId));
    }

    @Test
    public void testGetTeamPlayers_teamNotFound() {
        Long teamId = 1L;
        when(contractRepository.findByTeamId(teamId)).thenReturn(Optional.empty());
        assertThrows(TeamNotFoundException.class, () -> {
            contractService.getTeamPlayers(teamId);
        });
    }
}