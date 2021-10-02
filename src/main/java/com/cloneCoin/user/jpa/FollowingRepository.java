package com.cloneCoin.user.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FollowingRepository extends CrudRepository<FollowingEntity, Long> {
    Iterable<FollowingEntity> findAllByUserId(Long userId);
    Iterable<FollowingEntity> findAllByLeaderId(Long leaderId);
    Optional<FollowingEntity> findByUserIdAndLeaderId(Long userId, Long leaderId);
}
