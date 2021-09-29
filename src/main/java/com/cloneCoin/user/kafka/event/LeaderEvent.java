package com.cloneCoin.user.kafka.event;

import lombok.Data;

@Data
public class LeaderEvent {

    private String eventName;

    private Long userId;

    private String apiKey;

    private String secretKey;
}
