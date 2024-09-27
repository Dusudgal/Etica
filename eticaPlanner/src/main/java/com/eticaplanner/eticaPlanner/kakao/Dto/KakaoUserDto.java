package com.eticaplanner.eticaPlanner.kakao.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class KakaoUserDto {
    private String kakao_id;
    private String kakao_nickname;
    private String kakao_email;

}
