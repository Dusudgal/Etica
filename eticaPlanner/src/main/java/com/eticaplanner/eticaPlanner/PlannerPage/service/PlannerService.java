package com.eticaplanner.eticaPlanner.PlannerPage.service;

import com.eticaplanner.eticaPlanner.Admin.entity.TravelDTO;
import com.eticaplanner.eticaPlanner.Admin.entity.TravelEntity;
import com.eticaplanner.eticaPlanner.Admin.repository.TravelRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelDetailPlanEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelTitlePlanEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelPlanConverter;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TravelDetailPlanRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TravelTitlePlanRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.PlannerDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelDetailDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelResponseDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelTitlePlanDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Getter
public class PlannerService {

    private Boolean result;

    private final TravelTitlePlanRepository travelTitlePlanRepository;
    private final TravelDetailPlanRepository travelDetailPlanRepository;
    private final TravelRepository travelRepository;

    @Autowired
    public PlannerService(TravelTitlePlanRepository travelTitlePlanRepository , TravelDetailPlanRepository travelDetailPlanRepository , TravelRepository travelRepository) {
        this.travelTitlePlanRepository = travelTitlePlanRepository;
        this.travelDetailPlanRepository = travelDetailPlanRepository;
        this.travelRepository = travelRepository;
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

            TravelTitlePlanEntity savedata = travelTitlePlanRepository.save(TravelPlanConverter.travelTitlePlanDTOToEntity(tourTitleData , userid));
            if(savedata.getPlanNo() != null){
                List<TravelDetailPlanEntity> travelDetailPlans = TravelPlanConverter.travelDetailDTOToEntity(tourMemoData, savedata.getPlanNo(), userid);

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

    public PlannerDTO SelectPlan(String userId , String planTitle) {
        System.out.println("[PlannerService] SelectPlan");
        TravelTitlePlanDTO userTitlePlanInfo = TravelPlanConverter.travelTitlePlanEntityToDTO(travelTitlePlanRepository.findByUserIdAndPlanTitle(userId , planTitle));
        System.out.println("플랜넘버 : " + userTitlePlanInfo.getPlanNo());
        System.out.println("시작날짜 : " + userTitlePlanInfo.getStartDate());
        System.out.println("마지막날짜 : " + userTitlePlanInfo.getEndDate());
        if(userTitlePlanInfo != null){
            List<TravelDetailDTO> userPlanDetailInfo = TravelPlanConverter.travelDetailEntityToDTO(travelDetailPlanRepository.findByPlanNoAndUserId(userTitlePlanInfo.getPlanNo() , userId));
            // 로그 출력 (저장된 데이터 확인)
            for (TravelDetailDTO item : userPlanDetailInfo) {
                System.out.println("플랜 넘버 : " + item.getPlanNo());
                System.out.println("일수: " + item.getDate());
                System.out.println("이미지 주소: " + item.getImgSrc());
                System.out.println("관광지 주소: " + item.getAddr());
                System.out.println("관광지 이름: " + item.getTitle());
                System.out.println("관광 플랜 메모: " + item.getInputValue());
            }

            PlannerDTO userSelectinfo = new PlannerDTO();
            userSelectinfo.setTourMemoData(userPlanDetailInfo);
            userSelectinfo.setTourTitleData(userTitlePlanInfo);
            return userSelectinfo;
        }else{
            System.out.println("해당 플랜 정보를 찾을수가 없습니다.");
            return null;
        }
    }

    @Transactional
    public Boolean planModify(PlannerDTO planDto, String loginUser) {
        System.out.println("[PlannerService] planModify");

        TravelTitlePlanDTO tourTitleData = planDto.getTourTitleData();
        List<TravelDetailDTO> tourMemoData = planDto.getTourMemoData();

        result = false;

        if(tourTitleData != null &&
                !tourTitleData.getTour_title().isEmpty()){

            TravelTitlePlanEntity existingTitlePlan = travelTitlePlanRepository.findById(tourTitleData.getPlanNo()).orElse(null);
            System.out.println("1");
            if (existingTitlePlan != null) {
                // 제목, 시작일, 종료일 업데이트
                System.out.println("2");
                existingTitlePlan.setPlanTitle(tourTitleData.getTour_title());
                existingTitlePlan.setTravelStartDay(LocalDate.parse(tourTitleData.getStartDate()));
                existingTitlePlan.setTravelEndDay(LocalDate.parse(tourTitleData.getEndDate()));
                TravelTitlePlanEntity savedata = travelTitlePlanRepository.save(existingTitlePlan);

                if (savedata.getPlanNo() != null) {
                    List<TravelDetailPlanEntity> travelDetailPlans = TravelPlanConverter.travelDetailDTOToEntity(tourMemoData, savedata.getPlanNo(), loginUser);
                    System.out.println("3");
                    // TravelDetailPlanEntity 저장
                    travelDetailPlanRepository.deleteByPlanNoAndUserId(savedata.getPlanNo(), loginUser);
                    List<TravelDetailPlanEntity> detailSaveData = travelDetailPlanRepository.saveAll(travelDetailPlans);

                    // 로그 출력 (저장된 데이터 확인)
                    for (TravelDetailPlanEntity item : detailSaveData) {
                        System.out.println("일수: " + item.getPlanDurationDays());
                        System.out.println("이미지 주소: " + item.getTouristAttractionPhotoAddress());
                        System.out.println("관광지 주소: " + item.getTouristAttractionAddress());
                        System.out.println("관광지 이름: " + item.getTouristAttractionName());
                        System.out.println("관광 플랜 메모: " + item.getTouristAttractionText());
                        System.out.println("관광지 x좌표" + item.getPlanTouristMapx());
                        System.out.println("관광지 y좌표" + item.getPlanTouristMapy());
                    }
                    // 저장이 성공했음을 나타냄
                    result = true;
                }
            }else return result;
        }else return result;
        return result;
    }

    public List<TravelTitlePlanDTO> SelectPlanTitle(String sessionId) {
        System.out.println("[PlannerService] SelectPlanTitle");

        List<TravelTitlePlanDTO> userTitlePlanInfo = TravelPlanConverter.travelTitlePlansEntityToDTO(travelTitlePlanRepository.findByUserId(sessionId));

        return userTitlePlanInfo;
    }

    @Transactional
    public void deletePlan(TravelTitlePlanDTO planDTO, String userID) {
        System.out.println("[PlannerService] DeletePlan");
        travelTitlePlanRepository.deleteById(planDTO.getPlanNo());
        travelDetailPlanRepository.deleteByPlanNoAndUserId(planDTO.getPlanNo(), userID);
    }

//    public void getTravelData(String keyword) {
        public List<TravelResponseDTO> getTravelData(String keyword) {
        System.out.println("[PlannerService] getTravelData");
        List<TravelEntity> result = this.travelRepository.findByTravelNameLike("%"+keyword+"%");
        List<TravelResponseDTO> Dto = result.stream().map(TravelResponseDTO::new).collect(Collectors.toList());
        System.out.println(Dto);
        return Dto;
    }
}
