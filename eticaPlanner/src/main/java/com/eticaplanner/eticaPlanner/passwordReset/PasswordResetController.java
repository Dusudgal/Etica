package com.eticaplanner.eticaPlanner.passwordReset;

import com.eticaplanner.eticaPlanner.common.EncryptUtils;
import com.eticaplanner.eticaPlanner.email.service.EmailService;
import com.eticaplanner.eticaPlanner.passwordReset.entity.PasswordResetToken;
import com.eticaplanner.eticaPlanner.passwordReset.repository.PasswordResetTokenRepository;
import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @PostMapping("/reset-password/link")
    public ResponseEntity<?> sendResetLink(@RequestParam("email") String email) {
        // 이메일로 사용자 검색
        UserEntity user = userRepository.findByUserEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "error_message", "이메일이 존재하지 않습니다."));
        }

        // 비밀번호 재설정 토큰 생성 및 저장
        PasswordResetToken resetToken = new PasswordResetToken(user);
        tokenRepository.save(resetToken);

        // 재설정 링크 생성
        String resetUrl = "http://localhost:8080/user/reset-password?token=" + resetToken.getToken();

        // 이메일 전송 로직
        sendResetEmail(user.getUserEmail(), resetUrl);


        return ResponseEntity.ok(Map.of("code", 200, "message", "재설정 링크가 이메일로 전송되었습니다."));
    }

    private void sendResetEmail(String email, String resetUrl) {
        try {
            emailService.sendPasswordResetEmail(email, resetUrl);
            System.out.println("비밀번호 재설정 링크: " + resetUrl + " 를 " + email + "로 전송합니다.");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("비밀번호 재설정 이메일 전송에 실패했습니다.");
        }
    }

    @Transactional
    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                           @RequestParam("newPassword") String newPassword,
                                           @RequestParam("confirmPassword") String confirmPassword) {

        // 비밀번호 일치 여부 검사
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "error_message", "비밀번호가 일치하지 않습니다."));
        }

        // 토큰 검증
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        if (resetToken.isExpired()) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "error_message", "토큰이 만료되었습니다."));
        }
        /*
        // 아이디 검증
        UserEntity user = userRepository.findByUserId(String.valueOf(resetToken.getNo()))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));
        */
        // 비밀번호 해시화 및 업데이트
        UserEntity user = resetToken.getUser();
        // EncryptUtils.sha256(userDto.getUser_password());
        user.setUserPassword(EncryptUtils.sha256(newPassword));
        System.out.println("저장 전 비밀번호 : " + user.getUserPassword());
        userRepository.saveAndFlush(user); // 즉시 db에 변경사항을 적용하는 방식
        System.out.println("저장 후 확인 로그");
        return ResponseEntity.ok(Map.of("code", 200, "message", "비밀번호가 성공적으로 재설정되었습니다."));
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        // 토큰 검증
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        if (resetToken.isExpired()) {
            // 만료된 토큰 처리
            model.addAttribute("errorMessage", "토큰이 만료되었습니다.");
            return "error"; // 오류 페이지로 이동 (error.jsp 등)
        }

        model.addAttribute("token", token); // 비밀번호 재설정 페이지에 전달할 토큰
        model.addAttribute("viewName", "User/resetPassword");
        return "template/layout"; // 비밀번호 재설정 JSP 페이지 이름
    }
}
