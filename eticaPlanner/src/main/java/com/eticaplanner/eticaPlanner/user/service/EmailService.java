package com.eticaplanner.eticaPlanner.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String email, String resetUrl) throws MessagingException  {

        try {
            // MimeMessage 생성
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // 이메일 설정
            helper.setTo(email); // 수신자
            helper.setSubject("[Etica]비밀번호 재설정 요청"); // 제목

            // HTML 형식의 본문 내용
            String htmlContent = "<p>안녕하세요. EticaPlanner입니다.</p>" + "<p>다음 링크를 클릭하여 비밀번호를 재설정하세요:</p>" +
                    "<a href=\"" + resetUrl + "\">비밀번호 재설정</a>";
            helper.setText(htmlContent, true); // true를 통해 HTML 형식임을 설정

            // 이메일 전송
            mailSender.send(mimeMessage);
            System.out.println("비밀번호 재설정 이메일이 전송되었습니다: " + email);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("이메일 전송 중 오류 발생: " + e.getMessage());
        }
    }

}
