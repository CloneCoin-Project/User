package com.cloneCoin.user.service.impl;

import com.cloneCoin.user.dto.FollowingDto;
import com.cloneCoin.user.jpa.FollowingEntity;
import com.cloneCoin.user.jpa.FollowingRepository;
import com.cloneCoin.user.jpa.UserEntity;
import com.cloneCoin.user.jpa.UserRepository;
import com.cloneCoin.user.service.FollowingService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FollowingServiceImpl implements FollowingService {
    private FollowingRepository followingRepository;
    private UserRepository userRepository;

    public FollowingServiceImpl(FollowingRepository followingRepository, UserRepository userRepository) {
        this.followingRepository = followingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FollowingDto createFollowing(FollowingDto followingDto) {
        ModelMapper mapper = new ModelMapper();
        FollowingEntity followingEntity = mapper.map(followingDto, FollowingEntity.class);

        UserEntity user = userRepository.findById(followingEntity.getUserId()).get();
        UserEntity leader = userRepository.findById(followingEntity.getLeaderId()).get();

        followingEntity.setUserName(user.getName());
        followingEntity.setLeaderName(leader.getName());

        // 리더 검증 로직 추가 필요

        FollowingEntity newFollowing = followingRepository.save(followingEntity);

        log.info("follwing created : {}", newFollowing);
        return mapper.map(newFollowing, FollowingDto.class);
    }

    @Override
    public Iterable<FollowingDto> getFollowingsByUserId(Long userId) {
        Iterable<FollowingEntity> followingEntities =  followingRepository.findAllByUserId(userId);

        ModelMapper mapper = new ModelMapper();

        List<FollowingDto> followingDtoList = Arrays.asList(mapper.map(followingEntities, FollowingDto[].class));
        log.info("getFollowingsByUserId : {} ", followingDtoList);
        return followingDtoList;
    }

    @Override
    public Iterable<FollowingDto> getFollowersByLeaderId(Long leaderId) {
        Iterable<FollowingEntity> followingEntities =  followingRepository.findAllByLeaderId(leaderId);

        ModelMapper mapper = new ModelMapper();

        List<FollowingDto> followingDtoList = Arrays.asList(mapper.map(followingEntities, FollowingDto[].class));
        log.info("getFollowingsByUserId : {} ", followingDtoList);
        return followingDtoList;
    }
}
