package com.eticaplanner.eticaPlanner.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="user")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int userNo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_name" )
    private String userName;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_birth")
    private String userBirth;

    @Column(name = "user_gender")
    private String userGender;

    @Column(name = "user_image_path")
    private String userImagePath;

    @UpdateTimestamp
    @Column(name = "user_created_at", updatable = false)
    private ZonedDateTime userCreatedAt;

    @UpdateTimestamp
    @Column(name = "user_updated_at")
    private ZonedDateTime userUpdatedAt;
}
