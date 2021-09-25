package com.cloneCoin.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // json에서 null 은 버리도록
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
    private String role;
}
