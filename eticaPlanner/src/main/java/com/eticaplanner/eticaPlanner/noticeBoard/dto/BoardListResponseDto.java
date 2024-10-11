package com.eticaplanner.eticaPlanner.noticeBoard.dto;

import com.eticaplanner.eticaPlanner.noticeBoard.entity.Board;
import java.time.LocalDateTime;

public class BoardListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdAt;

    // 생성자
    public BoardListResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.createdAt = entity.getCreatedAt();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
