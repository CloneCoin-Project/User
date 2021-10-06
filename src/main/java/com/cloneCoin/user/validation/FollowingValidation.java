package com.cloneCoin.user.validation;

import com.cloneCoin.user.jpa.FollowingEntity;
import com.cloneCoin.user.jpa.FollowingRepository;
import com.cloneCoin.user.jpa.UserEntity;
import com.cloneCoin.user.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class FollowingValidation {
    FollowingRepository followingRepository;
    UserRepository userRepository;

    public FollowingValidation(FollowingRepository followingRepository, UserRepository userRepository) {
        this.followingRepository = followingRepository;
        this.userRepository = userRepository;
    }

    public String hasFollowingRelationship(Long userId, Long leaderId) {

        Optional<FollowingEntity> relationship = followingRepository.findByUserIdAndLeaderId(userId, leaderId);

        log.info("hasFollowingRelastionship : {}", relationship);

        if(!relationship.isEmpty()) {
            return "이미 팔로잉한 리더입니다.";
        }

        return "";
    }

    public String isLeader(Long leaderId) {

        Optional<UserEntity> user = userRepository.findById(leaderId);

        log.info("isLeader : {}" , user);
        if(user == null) {
            return "해당 사용자가 존재하지 않습니다.";
        }

        if(user.get().getRole() != "leader") {
            return "해당 사용자는 리더가 아닙니다.";
        }

        return "";
    }
}
