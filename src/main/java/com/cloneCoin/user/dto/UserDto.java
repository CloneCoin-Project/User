package com.cloneCoin.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String role;
    private String description;
    private String apiKey;
    private String secretKey;

    private Date createdAt;

    private String encryptedPassword;
}
