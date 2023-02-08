package com.teamanager.manager.service.VO;

import lombok.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long playerId;
    private long teamId;
    private Date date;
}
