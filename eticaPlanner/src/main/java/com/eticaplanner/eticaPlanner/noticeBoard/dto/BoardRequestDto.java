package com.eticaplanner.eticaPlanner.noticeBoard.dto;

public class BoardRequestDto {

    private String title;
    private String author;
    private String content;

    // 생성자
    public BoardRequestDto() {
    }

    public BoardRequestDto(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    // Getter 및 Setter
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
