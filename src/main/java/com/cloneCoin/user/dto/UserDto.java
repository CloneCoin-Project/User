package com.cloneCoin.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String userId;
    private String email;
    private String password;
    private String name;
    private String role;
    private Date createdAt;

    private String encryptedPassword;
}
