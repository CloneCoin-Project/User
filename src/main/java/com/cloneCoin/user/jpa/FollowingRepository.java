package com.cloneCoin.user.jpa;

import org.springframework.data.repository.CrudRepository;

public interface FollowingRepository extends CrudRepository<FollowingEntity, Long> {
    Iterable<FollowingEntity> findAllByUserId(Long userId);
    Iterable<FollowingEntity> findAllByLeaderId(Long leaderId);
}
