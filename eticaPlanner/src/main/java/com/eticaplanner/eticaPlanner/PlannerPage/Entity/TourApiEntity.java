package com.eticaplanner.eticaPlanner.PlannerPage.Entity;

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
    private Long id; // 기본 키 필드

    @Column(nullable = false)
    private String title;

    private String img;

    private String addr;

    private String mapx;

    private String mapy;

    public TourApiEntity(TourApiDTO apiDTO){
        this.img = apiDTO.getFirstimage2();
        this.addr = apiDTO.getAddr1() + apiDTO.getAddr2();
        this.title = apiDTO.getTitle();
        this.mapx = apiDTO.getMapx();
        this.mapy = apiDTO.getMapy();
    }

    public void updateFrom(TourApiEntity entity) {
        this.title = entity.title;
        this.img = entity.img;
        this.addr = entity.addr;
        this.mapx = entity.mapx;
        this.mapy = entity.mapy;
    }
}
