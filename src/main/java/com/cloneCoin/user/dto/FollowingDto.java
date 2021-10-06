package com.cloneCoin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long leaderId;
    private String leaderName;
    private String type;

}
