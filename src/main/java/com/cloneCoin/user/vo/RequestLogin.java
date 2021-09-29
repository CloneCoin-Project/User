package com.cloneCoin.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestLogin {

//    @NotNull(message = "Email cannot be null")
//    @Size(min = 2, message = "Email not be less than two characters")
//    @Email
//    private String email;

    @NotNull(message = "username cannot be null")
    private String username;

    @NotNull(message = "Password cannt be null")
    @Size(min = 8, message = "passwrod must be equal or greater than 8 char")
    private String password;
}
