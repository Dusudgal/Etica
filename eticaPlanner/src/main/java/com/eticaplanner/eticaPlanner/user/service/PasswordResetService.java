package com.eticaplanner.eticaPlanner.user.service;

import com.eticaplanner.eticaPlanner.user.entity.PasswordResetToken;
import com.eticaplanner.eticaPlanner.user.repository.PasswordResetTokenRepository;
import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public void createPasswordResetToken(String user_email) {

        // 이메일로 사용자 찾기
        UserEntity user = userRepository.findByUserEmail(user_email);

        // 사용자 존재 여부 확인
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 토큰 생성
        String token = UUID.randomUUID().toString();

        // 토큰 저장 (토큰과 유효기간)
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(1)) // 1시간 유효
                .build();

        tokenRepository.save(resetToken);

        // 이메일로 링크 전송
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        try {
            emailService.sendPasswordResetEmail(user.getUserEmail(), resetUrl);
        } catch (MessagingException e) {
            // 예외 처리 로직
            e.printStackTrace();
            throw new RuntimeException("비밀번호 재설정 이메일 전송에 실패했습니다.");
        }
    }
}
