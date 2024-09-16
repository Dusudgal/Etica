package com.eticaplanner.eticaPlanner.user.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int user_no; // 사용자 고유 번호
    private String user_id; // 사용자 ID
    private String user_password; // 사용자 비밀번호
    private String user_name; // 사용자 이름
    private String user_nickname; // 사용자 닉네임
    private String user_phone; // 사용자 전화번호
    private String user_birth; // 사용자 생년월일
    private String user_gender; // 사용자 성별
    private String user_image_path; // 사용자 이미지 경로
}
