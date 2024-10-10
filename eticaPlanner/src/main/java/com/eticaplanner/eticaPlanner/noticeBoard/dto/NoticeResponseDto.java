package com.eticaplanner.eticaPlanner.noticeBoard.dto;

import com.eticaplanner.eticaPlanner.noticeBoard.entity.Notice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NoticeResponseDto {

    private Long id;                     // 게시글 ID
    private String username;             // 사용자 이름
    private String contents;             // 내용
    private String title;                // 제목
    private String createdAt;             // 작성 시간 (String 타입으로 변경)

    // 생성자
    public NoticeResponseDto(Long id, String username, String title, String contents, LocalDateTime createdAt) {
        this.id = id;                    // ID 초기화
        this.username = username;        // 사용자 이름 초기화
        this.title = title;              // 제목 초기화
        this.contents = contents;        // 내용 초기화
        this.createdAt = convertToString(createdAt); // LocalDateTime을 String으로 변환
    }

    // Memo 엔티티를 기반으로 DTO 생성
    public NoticeResponseDto(Notice memo) {
        this.id = memo.getId();               // ID 초기화
        this.username = memo.getUsername();               // 사용자 이름을 "admin"으로 고정
        this.title = memo.getTitle();         // 제목 초기화
        this.contents = memo.getContents();   // 내용 초기화
        this.createdAt = convertToString(memo.getCreatedAt()); // LocalDateTime을 String으로 변환
    }

    // LocalDateTime을 String으로 변환하는 메서드
    private String convertToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    // Getter methods
    public Long getId() {
        return id;                             // ID getter
    }

    public String getUsername() {
        return username;                        // 사용자 이름 getter
    }

    public String getContents() {
        return contents;                        // 내용 getter
    }

    public String getTitle() {
        return title;                          // 제목 getter
    }

    public String getCreatedAt() {
        return createdAt;                      // 작성 시간 getter
    }

    // Setter methods (선택 사항: 필요에 따라 추가할 수 있습니다)
    public void setId(Long id) {
        this.id = id;                          // ID setter
    }

    public void setUsername(String username) {
        this.username = username;              // 사용자 이름 setter
    }

    public void setContents(String contents) {
        this.contents = contents;              // 내용 setter
    }

    public void setTitle(String title) {
        this.title = title;                    // 제목 setter
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;            // 작성 시간 setter
    }
}
