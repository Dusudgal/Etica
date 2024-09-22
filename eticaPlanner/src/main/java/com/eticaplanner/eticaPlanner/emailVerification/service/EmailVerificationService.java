//package com.eticaplanner.eticaPlanner.emailVerification.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class EmailVerificationService {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    // Redis에 저장할 만료 시간 설정(5분)
//    private static final long EXPIRATION_TIME = 5 * 60L; // 초 단위로 설정
//
//    // 토큰 생성 후 Redis에 저장하는 메서드
//    public String addToken(int userId, String purpose) {
//        String token = UUID.randomUUID().toString();
//
//        // Redis에 userId와 토큰을 저장하며, 만료 시간을 설정
//        String key = "emailVerification:" + userId + ":" + purpose;
//        redisTemplate.opsForValue().set(key, token, EXPIRATION_TIME, TimeUnit.SECONDS);
//
//        return token;
//    }
//
//    // 이메일 전송 메서드
//    public void sendEmail(String email, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject(subject);
//        message.setText(text);
//        javaMailSender.send(message);
//    }
//
//    // 이메일 인증 확인 메서드 (Redis에서 토큰을 확인)
//    public boolean verifyEmail(int userId, String token, String purpose) {
//        String key = "emailVerification:" + userId + ":" + purpose;
//
//        // Redis에서 토큰 조회
//        String storedToken = (String) redisTemplate.opsForValue().get(key);
//
//        // Redis에 해당하는 토큰이 없거나, 토큰이 일치하지 않으면 실패
//        if (storedToken == null || !storedToken.equals(token)) {
//            return false;
//        }
//
//        // 성공적으로 인증되었으므로 Redis에서 해당 토큰 삭제
//        redisTemplate.delete(key);
//        return true;
//    }
//}
