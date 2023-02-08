package com.teamanager.contract.service.model;

import com.teamanager.contract.service.VO.Player;
import com.teamanager.contract.service.VO.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long contractId;
    private Long playerId;
    private Long teamId;
}
