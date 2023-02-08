package com.teamanager.contract.service.repository;

import com.teamanager.contract.service.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByPlayerId(Long playerId);

    Optional<List<Contract>> findByTeamId(Long teamId);
}
