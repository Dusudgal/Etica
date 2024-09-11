package com.eticaplanner.eticaPlanner.PlannerPage.Entity;

import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelDetailDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelTitlePlanDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TravelTitlePlanConverter {

    public static TravelTitlePlan travelTitlePlanDTOToEntity(TravelTitlePlanDTO dto, String userId) {
        TravelTitlePlan entity = new TravelTitlePlan();
        entity.setUserId(userId); // DTO에는 userId가 없으므로 외부에서 설정해야 함
        entity.setPlanTitle(dto.getTour_title());
        entity.setTravelStartDay(LocalDate.parse(dto.getStartDate()));
        entity.setTravelEndDay(LocalDate.parse(dto.getEndDate()));
        // 기본값은 `false`로 설정되므로 별도로 설정할 필요 없음
        return entity;
    }

    public static List<TravelDetailPlanEntity> travelDetailDTOToEntity( List<TravelDetailDTO> dtos, Integer planNo, String userId) {

        List<TravelDetailPlanEntity> entityList = dtos.stream().map(dto -> {
            TravelDetailPlanEntity entity = new TravelDetailPlanEntity();
            entity.setPlanNo(planNo);
            entity.setUserId(userId);
            entity.setPlanDurationDays(Integer.parseInt(dto.getDate()));  // assuming 'date' is actually 'plan_duration_days'
            entity.setTouristAttractionName(dto.getTitle());
            entity.setTouristAttractionText(dto.getInputValue());  // assuming 'inputValue' is actually 'tourist_attraction_text'
            entity.setTouristAttractionAddress(dto.getAddr());
            entity.setTouristAttractionPhotoAddress(dto.getImgSrc());

            return entity;
        }).collect(Collectors.toList());

        return entityList;
    }
}
