package com.eticaplanner.eticaPlanner.noticeBoard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "memo")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 500)
    private String contents;

    @Column(length = 255)
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Notice(String username, String contents, String title) {
        this.username = username;
        this.contents = contents;
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}