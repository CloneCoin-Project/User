package com.cloneCoin.user.service;

import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.jpa.UserEntity;
import com.cloneCoin.user.vo.UserBasicFormForApi;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);
    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByUsername(String username);
    UserDto getUserDetailsByEmail(String email);

    UserDto applyLeader(UserBasicFormForApi userform);

    UserDto quitLeader(Long id);
}
