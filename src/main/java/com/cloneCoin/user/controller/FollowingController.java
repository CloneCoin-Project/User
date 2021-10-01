package com.cloneCoin.user.controller;

import com.cloneCoin.user.dto.FollowingDto;
import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.service.FollowingService;
import com.cloneCoin.user.vo.RequestFollowing;
import com.cloneCoin.user.vo.ResponseFollowing;
import com.cloneCoin.user.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class FollowingController {

    private FollowingService followingService;

    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @GetMapping("/follow/{userId}")
    public ResponseEntity<Iterable<ResponseFollowing>> getFollowingsByUserId(@PathVariable Long userId) {
        Iterable<FollowingDto> followingDtoList = followingService.getFollowingsByUserId(userId);

        ModelMapper mapper = new ModelMapper();
        List<ResponseFollowing> responseFollowings = Arrays.asList(mapper.map(followingDtoList, ResponseFollowing[].class));

        return ResponseEntity.status(HttpStatus.OK).body(responseFollowings);
    }

    @PostMapping("/follow")
    public ResponseEntity<ResponseFollowing> followLeader(@RequestBody RequestFollowing requestFollowing) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        FollowingDto followingDto = mapper.map(requestFollowing, FollowingDto.class);

        FollowingDto newFollowing = followingService.createFollowing(followingDto);

        ResponseFollowing responseFollowing = mapper.map(newFollowing, ResponseFollowing.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseFollowing); // 201 success
    }


}
