package com.cloneCoin.user.controller;

import com.cloneCoin.user.dto.FollowingDto;
import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.service.FollowingService;
import com.cloneCoin.user.vo.RequestFollowing;
import com.cloneCoin.user.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FollowingController {

    private FollowingService followingService;

    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @PostMapping("/follow")
    public ResponseEntity<FollowingDto> followLeader(@RequestBody RequestFollowing requestFollowing) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        FollowingDto followingDto = mapper.map(requestFollowing, FollowingDto.class);

        FollowingDto newFollowing = followingService.createFollowing(followingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newFollowing); // 201 success
    }


}
