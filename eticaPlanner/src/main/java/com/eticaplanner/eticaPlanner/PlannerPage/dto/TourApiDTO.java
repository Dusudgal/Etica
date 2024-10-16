package com.eticaplanner.eticaPlanner.PlannerPage.dto;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TourApiEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TourApiDTO {

    private String firstimage2;
    private String addr1;
    private String addr2;
    private String mapx;
    private String mapy;
    private String title;

    public TourApiDTO(TourApiEntity entity){
        this.firstimage2 = entity.getImg();
        this.title = entity.getTitle();
        this.addr1 = entity.getAddr();
        this.addr2 = "";
        this.mapx = entity.getMapx();
        this.mapy = entity.getMapy();
    }

    public TourApiDTO(TravelResponseDTO dto) {
        this.title = dto.getTitle();
        this.addr1 = dto.getAddr1();
        this.addr2 = dto.getAddr2();
        this.mapx = dto.getMapx();
        this.mapy = dto.getMapy();
        this.firstimage2 = dto.getFirstimage(); // firstimage를 어떻게 설정할지에 따라 조정
    }
}
