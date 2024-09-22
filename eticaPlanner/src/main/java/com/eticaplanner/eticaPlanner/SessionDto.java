package com.eticaplanner.eticaPlanner;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    // user
    private String user_id; // 사용자 ID
    private String user_name; // 사용자 이름
    private String user_nickname; // 사용자 닉네임
    // kakao_user
    private String kakao_id; // 사용자 카카오 ID
    private String kakao_nickname; // 사용자 카카오 닉네임
    private String kakao_email; // 사용자 카카오 이메일
    private String kakao_accessToken;
}
