package com.eticaplanner.eticaPlanner.emailVerification.service;

import com.eticaplanner.eticaPlanner.emailVerification.entity.EmailVerificationEntity;
import com.eticaplanner.eticaPlanner.emailVerification.repository.EmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    // 이메일 인증 여부 확인
    public boolean isEmailVerified(int user_id){
        // 이메일 인증 엔티티 조회(null이면 인증 완료)
        EmailVerificationEntity verification = emailVerificationRepository.findByUserId(user_id);
        return verification == null;
    }
    /* 구현중!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // 이메일 인증 토큰 생성
    public String createVerificationToken(int user_id, String purpose){
        String token = UUID.randomUUID().toString();
        EmailVerificationEntity emailVerification = EmailVerificationEntity.builder()
                .userId(user_id)
                .token(token)
                .purpose(purpose)
                .build();
        EmailVerificationRepository.save(emailVerification);
        return token;
    }
     */

    // 이메일 발송
    public void sendVerificationEmail(String email, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
    }
}
