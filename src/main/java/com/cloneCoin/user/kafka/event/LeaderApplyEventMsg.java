package com.cloneCoin.user.kafka.event;

import lombok.Data;

@Data
public class LeaderApplyEventMsg {

    private Long leaderId;

    private String leaderName;

    private String apiKey;

    private String secretKey;
}
