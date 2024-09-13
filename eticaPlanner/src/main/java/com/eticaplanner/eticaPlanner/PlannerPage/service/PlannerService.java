package com.eticaplanner.eticaPlanner.PlannerPage.service;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelDetailPlanEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelTitlePlan;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelTitlePlanConverter;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TravelDetailPlanRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TravelTitlePlanRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.PlannerDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelDetailDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelTitlePlanDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@Service
@Getter
public class PlannerService {

    private Boolean result;

    private final TravelTitlePlanRepository travelTitlePlanRepository;
    private final TravelDetailPlanRepository travelDetailPlanRepository;

    @Autowired
    public PlannerService(TravelTitlePlanRepository travelTitlePlanRepository , TravelDetailPlanRepository travelDetailPlanRepository) {
        this.travelTitlePlanRepository = travelTitlePlanRepository;
        this.travelDetailPlanRepository = travelDetailPlanRepository;
    }

    public Boolean planCreate(PlannerDTO data, String userid){
        System.out.println("[PlannerService] PlanCreate");
        TravelTitlePlanDTO tourTitleData = data.getTourTitleData();
        List<TravelDetailDTO> tourMemoData = data.getTourMemoData();
        result = false;
        if(tourTitleData != null &&
            !tourTitleData.getTour_title().isEmpty()){
            System.out.println("Tour Title: " + tourTitleData.getTour_title());
            System.out.println("Start Date: " + tourTitleData.getStartDate());
            System.out.println("End Date: " + tourTitleData.getEndDate());

            TravelTitlePlan savedata = travelTitlePlanRepository.save(TravelTitlePlanConverter.travelTitlePlanDTOToEntity(tourTitleData , userid));
            if(savedata.getPlanNo() != null){
                List<TravelDetailPlanEntity> travelDetailPlans = TravelTitlePlanConverter.travelDetailDTOToEntity(tourMemoData, savedata.getPlanNo(), userid);

                // TravelDetailPlanEntity 저장
                List<TravelDetailPlanEntity> detailSaveData = travelDetailPlanRepository.saveAll(travelDetailPlans);

                // 로그 출력 (저장된 데이터 확인)
                for (TravelDetailPlanEntity item : detailSaveData) {
                    System.out.println("일수: " + item.getPlanDurationDays());
                    System.out.println("이미지 주소: " + item.getTouristAttractionPhotoAddress());
                    System.out.println("관광지 주소: " + item.getTouristAttractionAddress());
                    System.out.println("관광지 이름: " + item.getTouristAttractionName());
                    System.out.println("관광 플랜 메모: " + item.getTouristAttractionText());
                }
                // 저장이 성공했음을 나타냄
                result = true;
            }
            else return result;
        } else return result;
        return result;
    }
}
