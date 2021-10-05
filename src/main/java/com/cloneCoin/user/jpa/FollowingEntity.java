package com.cloneCoin.user.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
