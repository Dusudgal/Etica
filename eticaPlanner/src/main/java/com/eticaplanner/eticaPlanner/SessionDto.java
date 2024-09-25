package com.eticaplanner.eticaPlanner;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDto {
    private String user_id; // 사용자 ID
    private String user_name; // 사용자 이름
    private String user_nickname; // 사용자 닉네임
    private String user_birth; // 사용자 생년월일
}
