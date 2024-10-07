package com.eticaplanner.eticaPlanner.noticeBoard.dto;

import com.eticaplanner.eticaPlanner.noticeBoard.entity.Board;
import java.time.LocalDateTime;

public class BoardResponseDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createdAt; // createdAt으로 변경

    // 생성자 (엔티티를 기반으로 DTO 생성)
    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt(); // 엔티티에서 createdDate 가져옴
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() { // 메소드 이름을 createdAt으로 변경
        return createdAt; // 이제 정확한 필드 이름을 반환
    }
}
