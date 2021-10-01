package com.cloneCoin.user.jpa;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="followings")
public class FollowingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;
//    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Long leaderId;
//    @Column(nullable = false)
    private String leaderName;
}
