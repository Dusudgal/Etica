package com.eticaplanner.eticaPlanner.PlannerPage.dto;

import com.eticaplanner.eticaPlanner.Admin.entity.TravelDTO;
import com.eticaplanner.eticaPlanner.Admin.entity.TravelEntity;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TravelResponseDTO {
    String addr1;
    String addr2;
    String mapx;
    String mapy;
    String title;
    String firstimage;

    public TravelResponseDTO(TravelEntity entity) {
        this.mapx = entity.getTravelXcd();
        this.mapy = entity.getTravelYcd();
        this.title = entity.getTravelName();
    }
}
