package com.eticaplanner.eticaPlanner.review.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReviewDto {

    private String tourTitle;
    private Integer reviewId;  // 리뷰 고유 번호
    private String reviewTitle;     // 리뷰 제목
    private String reviewContent;      // 리뷰 내용



}
