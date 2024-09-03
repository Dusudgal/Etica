package com.eticaplanner.eticaPlanner.mypage.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name="user")
@Entity
public class MyPageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNO;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_phone", nullable = false, unique = true)
    private String userPhone;

    @Column(name = "user_birth", nullable = false)
    private String userBirth;

    @Column(name = "user_gender", nullable = false)
    private String userGender;

    @Column(name = "user_image_path")
    private String userImagePath;

    @Column(name = "user_created_at", updatable = false)
    @CreationTimestamp
    private ZonedDateTime userCreatedAt;

    @UpdateTimestamp
    @Column(name = "user_updated_at")
    private ZonedDateTime userUpdatedAt;

}
