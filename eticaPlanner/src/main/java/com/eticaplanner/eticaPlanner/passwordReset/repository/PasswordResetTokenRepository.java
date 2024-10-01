package com.eticaplanner.eticaPlanner.passwordReset.repository;

import com.eticaplanner.eticaPlanner.passwordReset.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByExpiryDateBefore(LocalDateTime now); // 만료된 토큰 삭제

}
