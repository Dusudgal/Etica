package com.eticaplanner.eticaPlanner.passwordReset.entity;

import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @OneToOne
    @JoinColumn(name = "user_no", referencedColumnName = "user_no")
    private UserEntity user;

    private String token;

    private LocalDateTime expiryDate;


    // UserEntity를 인자로 받는 생성자 추가
    public PasswordResetToken(UserEntity user) {
        this.user = user;
        this.token = generateToken(); // 여기에 토큰 생성 로직 구현
        this.expiryDate = LocalDateTime.now().plusHours(1); // 1시간 후 만료
    }

    // UUID를 사용하여 토큰 생성하는 메서드
    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
