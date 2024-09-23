package com.eticaplanner.eticaPlanner.emailVerification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "email_verification")
@Entity
public class EmailVerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(name = "user_id")
    private int userId;

    private String token;

    private String purpose;

    @UpdateTimestamp
    @Column(name = "createdAt", updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "expiredAt")
    private ZonedDateTime expiredAt;
}
