package com.eticaplanner.eticaPlanner.PlannerPage.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PlannerDTO {
    private TravelTitlePlanDTO tourTitleData;
    private List<TravelDetailDTO> tourMemoData;
}
