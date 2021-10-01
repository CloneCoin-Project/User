package com.cloneCoin.user.service;

import com.cloneCoin.user.dto.FollowingDto;
import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.jpa.UserEntity;
import com.cloneCoin.user.vo.UserBasicFormForApi;

import java.util.List;

public interface FollowingService {
    FollowingDto createFollowing(FollowingDto followingDto);
    Iterable<FollowingDto> getFollowingsByUserId(Long userId);

//    Iterable<FollowingDto> getLeadersByUserId(Long userId);
//    Iterable<FollowingDto> getUsersByLeaderId(Long leaderId);
}
