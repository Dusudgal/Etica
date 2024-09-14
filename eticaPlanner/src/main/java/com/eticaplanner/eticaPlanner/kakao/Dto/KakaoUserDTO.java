package com.eticaplanner.eticaPlanner.kakao.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class KakaoUserDTO {
    private String kakaoId;
    private String nickname;
    private String email;
}
