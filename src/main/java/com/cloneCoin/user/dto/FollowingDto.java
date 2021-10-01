package com.cloneCoin.user.dto;

import lombok.Data;

@Data
public class FollowingDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long leaderId;
    private String leaderName;
}
