package com.cloneCoin.user.service;

import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
