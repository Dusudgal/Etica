package com.eticaplanner.eticaPlanner.Admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="admin_Member")
@Entity
public class AdminEntity {

    @Id//primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto_increment
    @Column(name="admin_no")
    private int adminNo;

    @Column(name="admin_approval", columnDefinition = "int default 0")
    private int adminApproval;

    @Column(name="admin_id", nullable = false)
    private String adminId;

    @Column(name="admin_pw", nullable = false)
    private String adminPw;

    @Column(name="admin_name", nullable = false)
    private String adminName;

    @Column(name="admin_phone", nullable = false)
    private String adminPhone;

    @Column(name="admin_email", nullable = false)
    private String adminEmail;

    @Column(name="admin_loginattempts")
    private int adminLoginattempts;

    @Column(name="admin_accountlocked")
    private boolean adminAccountlocked;

    @Column(name="admin_locktime")
    private LocalDateTime adminLocktime;

}
