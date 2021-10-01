package com.cloneCoin.user.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserCreateMsg {
    @JsonProperty("userId")
    Long userId;
}
