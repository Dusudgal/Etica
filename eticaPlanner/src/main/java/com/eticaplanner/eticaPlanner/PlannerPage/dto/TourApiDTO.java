package com.eticaplanner.eticaPlanner.PlannerPage.dto;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TourApiEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class TourApiDTO {

    private String firstimage;
    private String addr1;
    private String addr2;
    private String mapx;
    private String mapy;
    private String title;

    public TourApiDTO(TourApiEntity entity){
        this.firstimage = entity.getTour_img();
        this.title = entity.getTour_title();
        this.addr1 = entity.getTour_addr();
        this.addr2 = "";
        this.mapx = entity.getTour_mapx();
        this.mapy = entity.getTour_mapy();
    }

    public TourApiDTO(TravelResponseDTO dto) {
        this.title = dto.getTitle();
        this.addr1 = dto.getAddr1();
        this.addr2 = dto.getAddr2();
        this.mapx = dto.getMapx();
        this.mapy = dto.getMapy();
        this.firstimage = dto.getFirstimage(); // firstimage를 어떻게 설정할지에 따라 조정
    }
}
