package com.cloneCoin.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // json에서 null 은 버리도록
public class UserBasicFormForApi {
    private Long userId;

    private Long leaderId;

    private String apiKey;

    private String secretKey;
}
