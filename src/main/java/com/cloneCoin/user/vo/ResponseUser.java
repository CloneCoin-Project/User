package com.cloneCoin.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // json에서 null 은 버리도록
public class ResponseUser {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String role;
}
