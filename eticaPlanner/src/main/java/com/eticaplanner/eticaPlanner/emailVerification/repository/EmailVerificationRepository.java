package com.eticaplanner.eticaPlanner.emailVerification.repository;

import com.eticaplanner.eticaPlanner.emailVerification.entity.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Integer> {
    // 이메일 인증 엔티티 조회
    EmailVerificationEntity findByUserId(int userId);
}
