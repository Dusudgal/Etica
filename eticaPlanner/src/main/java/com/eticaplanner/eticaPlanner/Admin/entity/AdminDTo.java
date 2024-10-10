package com.eticaplanner.eticaPlanner.Admin.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminDTo {

    private String adminId;
    private String adminPw;
    private String adminName;
    private String adminPhone;
    private String adminEmail;
    private int adminLoginattempts; //로그인 시도 횟수
    private boolean adminAccountlocked; //계정 잠금 여부
    private LocalDateTime adminLocktime; // 잠금 시간
}
