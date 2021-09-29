package com.cloneCoin.user.kafka.event;

import lombok.Data;

@Data
public class LeaderApplyEvent {

    private String eventName;

    private Long userId;

    private String apiKey;

    private String secretKey;
}
