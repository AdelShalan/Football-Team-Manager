package com.teamanager.team.service.VO;

import com.teamanager.team.service.model.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplate {
    private Team team;
    private Manager manager;
    private Set<Player> players;
}
