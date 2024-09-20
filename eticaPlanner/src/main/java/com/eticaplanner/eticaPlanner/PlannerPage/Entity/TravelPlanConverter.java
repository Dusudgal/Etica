package com.eticaplanner.eticaPlanner.PlannerPage.Entity;

import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelDetailDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelTitlePlanDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TravelPlanConverter {

    public static TravelTitlePlanEntity travelTitlePlanDTOToEntity(TravelTitlePlanDTO dto, String userId) {
        TravelTitlePlanEntity entity = new TravelTitlePlanEntity();
        entity.setUserId(userId); // DTO에는 userId가 없으므로 외부에서 설정해야 함
        entity.setPlanTitle(dto.getTour_title());
        entity.setTravelStartDay(LocalDate.parse(dto.getStartDate()));
        entity.setTravelEndDay(LocalDate.parse(dto.getEndDate()));
        // 기본값은 `false`로 설정되므로 별도로 설정할 필요 없음
        return entity;
    }

    public static TravelTitlePlanDTO travelTitlePlanEntityToDTO(TravelTitlePlanEntity Entity) {
        TravelTitlePlanDTO dto = new TravelTitlePlanDTO();
        dto.setPlanNO(Entity.getPlanNo());
        dto.setTour_title(Entity.getPlanTitle());
        dto.setStartDate(String.valueOf(Entity.getTravelStartDay()));
        dto.setEndDate(String.valueOf(Entity.getTravelEndDay()));
        // 기본값은 `false`로 설정되므로 별도로 설정할 필요 없음
        return dto;
    }

    public static List<TravelDetailPlanEntity> travelDetailDTOToEntity( List<TravelDetailDTO> dtos, Integer planNo, String userId) {

        List<TravelDetailPlanEntity> entityList = dtos.stream().map(dto -> {
            TravelDetailPlanEntity entity = new TravelDetailPlanEntity();
            entity.setPlanNo(planNo);
            entity.setUserId(userId);
            entity.setPlanDurationDays(dto.getDate());
            entity.setTouristAttractionName(dto.getTitle());
            entity.setTouristAttractionText(dto.getInputValue());
            entity.setTouristAttractionAddress(dto.getAddr());
            entity.setTouristAttractionPhotoAddress(dto.getImgSrc());

            return entity;
        }).collect(Collectors.toList());

        return entityList;
    }

    public static List<TravelDetailDTO> travelDetailEntityToDTO(List<TravelDetailPlanEntity> entitys) {

        List<TravelDetailDTO> dtoList = entitys.stream().map(entity -> {
            TravelDetailDTO dto = new TravelDetailDTO();
            dto.setTitle(entity.getTouristAttractionName());
            dto.setAddr(entity.getTouristAttractionAddress());
            dto.setDate(entity.getPlanDurationDays());
            dto.setImgSrc(entity.getTouristAttractionPhotoAddress());
            dto.setInputValue(entity.getTouristAttractionText());
            return dto;
        }).collect(Collectors.toList());

        return dtoList;
    }
}
