package com.eticaplanner.eticaPlanner.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_review")
@Getter
@Setter
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    private Integer reviewId; // 리뷰 고유 번호

//    @Column(name = "travel_no", nullable = false)
//    private Integer travelNo; // 관광지 고유 번호

    @Column(name = "user_id", nullable = false, length = 20) // 길이 맞추기
    private String userId; // 사용자 ID

    @Column(name = "re_title", length = 100)
    private String reTitle; // 리뷰 제목

    @Column(name = "re_text", columnDefinition = "TEXT")
    private String reText; // 리뷰 내용

    @Column(name = "re_date", nullable = false)
    private LocalDateTime reDate; // 생성일

    @Column(name = "re_edit_date")
    private LocalDateTime reEditDate; // 수정일

}
