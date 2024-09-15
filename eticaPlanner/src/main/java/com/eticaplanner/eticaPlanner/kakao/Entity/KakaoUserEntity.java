package com.eticaplanner.eticaPlanner.kakao.Entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="kakao_user")
@Entity
public class KakaoUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kakao_no")
    private Long id; // // Entity에서의 id는 실제 DB의 'kakao_no'에 매핑됨

    @Column(name = "kakao_id", nullable = false, unique = true)
    private Long  kakaoId;

    @Column(name = "kakao_nickname", nullable = false)
    private String nickname;

    @Column(name = "kakao_email", unique = true, nullable = false)
    private String email;
}
