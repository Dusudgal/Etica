package com.eticaplanner.eticaPlanner.PlannerPage.Entity;

import com.eticaplanner.eticaPlanner.Admin.entity.TravelDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TourApiDTO;
import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Tour_Data")
public class TourApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성 전략
    private Long tour_no; // 기본 키 필드

    @Column(nullable = false)
    private String tour_title;

    private String tour_img;

    private String tour_addr;

    private String tour_mapx;

    private String tour_mapy;

    public TourApiEntity(TourApiDTO apiDTO){
        this.tour_img = apiDTO.getFirstimage();
        this.tour_addr = apiDTO.getAddr1() + apiDTO.getAddr2();
        this.tour_title = apiDTO.getTitle();
        this.tour_mapx = apiDTO.getMapx();
        this.tour_mapy = apiDTO.getMapy();
    }

    public TourApiEntity(TravelDTO travelDTO) {
        this.tour_img = "";
        this.tour_addr = travelDTO.getTravel_context();
        this.tour_title = travelDTO.getTravel_name();
        this.tour_mapx = travelDTO.getTravel_X_marker();
        this.tour_mapy = travelDTO.getTravel_Y_marker();
    }

    public void updateFrom(TourApiEntity entity) {
        this.tour_title = entity.tour_title;
        this.tour_img = entity.tour_img;
        this.tour_addr = entity.tour_addr;
        this.tour_mapx = entity.tour_mapx;
        this.tour_mapy = entity.tour_mapy;
    }
}
