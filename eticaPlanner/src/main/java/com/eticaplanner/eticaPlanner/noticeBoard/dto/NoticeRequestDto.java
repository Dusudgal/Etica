package com.eticaplanner.eticaPlanner.noticeBoard.dto;

public class NoticeRequestDto {
    private String title;              // 제목 필드
    private String contents;           // 내용

    // Constructors
    public NoticeRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;                  // 제목 getter
    }

    public void setTitle(String title) {
        this.title = title;           // 제목 setter
    }

    public String getContents() {
        return contents;               // 내용 getter
    }

    public void setContents(String contents) {
        this.contents = contents;      // 내용 setter
    }
}
