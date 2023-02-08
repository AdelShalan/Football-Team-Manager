package com.teamanager.contract.service.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    private Long id;
    private String name;
    private Long managerId;
}
