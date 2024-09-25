package com.eticaplanner.eticaPlanner.PlannerPage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelDetailDTO {
    // 플랜 넘버
    private int PlanNo;
    // 일수
    private int date;
    // 이미지 주소
    private String imgSrc;
    // 관광지 이름
    private String title;
    // 관광 플랜 메모 내용
    private String addr;
    // 관광지 주소
    private String inputValue;
}
