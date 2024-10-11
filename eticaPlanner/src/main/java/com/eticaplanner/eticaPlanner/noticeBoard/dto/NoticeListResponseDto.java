package com.eticaplanner.eticaPlanner.noticeBoard.dto;

import com.eticaplanner.eticaPlanner.noticeBoard.entity.Notice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NoticeListResponseDto {

    private Long id;
    private String title;
    private String username; // 사용자 이름
    private String createdAt; // createdAt을 String으로 변경

    // 생성자
    public NoticeListResponseDto(Notice entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.username = entity.getUsername(); // 사용자 이름 설정
        // LocalDateTime을 포맷팅하여 String으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.createdAt = entity.getCreatedAt().format(formatter); // 포맷된 문자열로 저장
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getCreatedAt() {
        return createdAt; // String 형식으로 반환
    }
}
