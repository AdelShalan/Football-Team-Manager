package com.teamanager.team.service.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Long Id;
    private String name;
    private Integer age;
    private Position position;
}
