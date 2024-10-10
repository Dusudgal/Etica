package com.eticaplanner.eticaPlanner.noticeBoard.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 ID

    private String title; // 글 제목

    private String author; // 작성자

    private LocalDateTime createdAt; // 작성 시간

    @Lob
    private String content; // 글 내용

    // 기본 생성자
    public Board() {
    }

    public Board(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    @PrePersist // 엔티티가 저장되기 전에 호출
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // 현재 시간을 설정
    }

    // Getter와 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt; // createdAt getter
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt; // createdAt setter
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
