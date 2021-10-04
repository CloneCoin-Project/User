package com.cloneCoin.user.controller;

import com.cloneCoin.user.dto.FollowingDto;
import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.service.FollowingService;
import com.cloneCoin.user.validation.FollowingValidation;
import com.cloneCoin.user.vo.RequestFollowing;
import com.cloneCoin.user.vo.ResponseFollowing;
import com.cloneCoin.user.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class FollowingController {

    private FollowingService followingService;
    private FollowingValidation followingValidation;

    public FollowingController(FollowingService followingService, FollowingValidation followingValidation) {
        this.followingService = followingService;
        this.followingValidation = followingValidation;
    }

    @GetMapping("/followings")
    public ResponseEntity<Iterable<ResponseFollowing>> getFollowingsByUserId(@RequestParam Long userId) {
        Iterable<FollowingDto> followingDtoList = followingService.getFollowingsByUserId(userId);

        ModelMapper mapper = new ModelMapper();
        List<ResponseFollowing> responseFollowings = Arrays.asList(mapper.map(followingDtoList, ResponseFollowing[].class));

        return ResponseEntity.status(HttpStatus.OK).body(responseFollowings);
    }

    @GetMapping("/followers")
    public ResponseEntity<Iterable<ResponseFollowing>> getFollowersByLeaderId(@RequestParam Long leaderId) {
        Iterable<FollowingDto> followingDtoList = followingService.getFollowersByLeaderId(leaderId);

        ModelMapper mapper = new ModelMapper();
        List<ResponseFollowing> responseFollowings = Arrays.asList(mapper.map(followingDtoList, ResponseFollowing[].class));

        return ResponseEntity.status(HttpStatus.OK).body(responseFollowings);
    }

    @PostMapping("/follow")
    public ResponseEntity<Object> followLeader(@RequestBody RequestFollowing requestFollowing) {

        Long userId = requestFollowing.getUserId();
        Long leaderId = requestFollowing.getLeaderId();
        // leader 인지 검증작업 필요.

        String validationMsg = "";

        validationMsg = followingValidation.isLeader(leaderId);
        if(validationMsg != "") {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(validationMsg);
        }

        validationMsg = followingValidation.hasFollowingRelationship(userId, leaderId);
        if(validationMsg != "") {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(validationMsg);
        }

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        FollowingDto followingDto = mapper.map(requestFollowing, FollowingDto.class);


        FollowingDto newFollowing = followingService.createFollowing(followingDto);

        ResponseFollowing responseFollowing = mapper.map(newFollowing, ResponseFollowing.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseFollowing); // 201 success
    }


}
