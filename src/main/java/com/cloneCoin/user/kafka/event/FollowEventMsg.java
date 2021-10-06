package com.cloneCoin.user.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowEventMsg {
    private Long userId;
    private String userName;
    private Long leaderId;
    private String leaderName;
    private String type; // "follow" or "unfollow"
}
