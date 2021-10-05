package com.cloneCoin.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseFollowing {
    private Long userId;
    private String userName;
    private Long leaderId;
    private String leaderName;
    private String type;
}
