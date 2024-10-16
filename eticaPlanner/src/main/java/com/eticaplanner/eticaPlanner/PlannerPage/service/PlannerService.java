package com.eticaplanner.eticaPlanner.PlannerPage.service;

import com.eticaplanner.eticaPlanner.Admin.entity.TravelEntity;
import com.eticaplanner.eticaPlanner.Admin.repository.TravelRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TourApiEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelDetailPlanEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelTitlePlanEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelPlanConverter;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TourApiRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TravelDetailPlanRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TravelTitlePlanRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private final TourApiRepository tourApiRepository;

    @Autowired
    public PlannerService(TravelTitlePlanRepository travelTitlePlanRepository , TravelDetailPlanRepository travelDetailPlanRepository , TravelRepository travelRepository , TourApiRepository tourApiRepository) {
        this.travelTitlePlanRepository = travelTitlePlanRepository;
        this.travelDetailPlanRepository = travelDetailPlanRepository;
        this.travelRepository = travelRepository;
        this.tourApiRepository = tourApiRepository;
    }

    public Boolean planCreate(PlannerDTO data, String userid){
        System.out.println("[PlannerService] PlanCreate");
        TravelTitlePlanDTO tourTitleData = data.getTourTitleData();
        List<TravelDetailDTO> tourMemoData = data.getTourMemoData();
        result = false;
        if(tourTitleData != null &&
            !tourTitleData.getTour_title().isEmpty()){

            TravelTitlePlanEntity savedata = travelTitlePlanRepository.save(TravelPlanConverter.travelTitlePlanDTOToEntity(tourTitleData , userid));
            if(savedata.getPlanNo() != null){
                List<TravelDetailPlanEntity> travelDetailPlans = TravelPlanConverter.travelDetailDTOToEntity(tourMemoData, savedata.getPlanNo(), userid);

                // TravelDetailPlanEntity 저장
                List<TravelDetailPlanEntity> detailSaveData = travelDetailPlanRepository.saveAll(travelDetailPlans);
                result = true;
            }
            else return result;
        } else return result;
        return result;
    }

    public PlannerDTO SelectPlan(String userId , String planTitle) {
        System.out.println("[PlannerService] SelectPlan");
        TravelTitlePlanDTO userTitlePlanInfo = TravelPlanConverter.travelTitlePlanEntityToDTO(travelTitlePlanRepository.findByUserIdAndPlanTitle(userId , planTitle));
        List<TravelDetailDTO> userPlanDetailInfo = TravelPlanConverter.travelDetailEntityToDTO(travelDetailPlanRepository.findByPlanNoAndUserId(userTitlePlanInfo.getPlanNo(), userId));

        PlannerDTO userSelectinfo = new PlannerDTO();
        userSelectinfo.setTourMemoData(userPlanDetailInfo);
        userSelectinfo.setTourTitleData(userTitlePlanInfo);
        return userSelectinfo;
    }

//    @Transactional
//    public Boolean planModify(PlannerDTO planDto, String loginUser) {
//        System.out.println("[PlannerService] planModify");
//
//        TravelTitlePlanDTO tourTitleData = planDto.getTourTitleData();
//        List<TravelDetailDTO> tourMemoData = planDto.getTourMemoData();
//
//        result = false;
//
//        if(tourTitleData != null &&
//                !tourTitleData.getTour_title().isEmpty()){
//
//            TravelTitlePlanEntity existingTitlePlan = travelTitlePlanRepository.findById(tourTitleData.getPlanNo()).orElse(null);
//            if (existingTitlePlan != null) {
//                // 제목, 시작일, 종료일 업데이트
//                existingTitlePlan.setPlanTitle(tourTitleData.getTour_title());
//                existingTitlePlan.setTravelStartDay(LocalDate.parse(tourTitleData.getStartDate()));
//                existingTitlePlan.setTravelEndDay(LocalDate.parse(tourTitleData.getEndDate()));
//                TravelTitlePlanEntity savedata = travelTitlePlanRepository.save(existingTitlePlan);
//
//                if (savedata.getPlanNo() != null) {
//                    List<TravelDetailPlanEntity> travelDetailPlans = TravelPlanConverter.travelDetailDTOToEntity(tourMemoData, savedata.getPlanNo(), loginUser);
//                    // TravelDetailPlanEntity 저장
//                    travelDetailPlanRepository.deleteByPlanNoAndUserId(savedata.getPlanNo(), loginUser);
//                    List<TravelDetailPlanEntity> detailSaveData = travelDetailPlanRepository.saveAll(travelDetailPlans);
//                    result = true;
//                }
//            }else return result;
//        }else return result;
//        return result;
//    }
    @Transactional
    public Boolean planModify(PlannerDTO planDto, String loginUser) {
        System.out.println("[PlannerService] planModify");

        TravelTitlePlanDTO tourTitleData = planDto.getTourTitleData();
        List<TravelDetailDTO> tourMemoData = planDto.getTourMemoData();

        if (tourTitleData == null || tourTitleData.getTour_title().isEmpty()) {
            return false; // 조건에 맞지 않으면 false 반환
        }

        TravelTitlePlanEntity existingTitlePlan = travelTitlePlanRepository.findById(tourTitleData.getPlanNo()).orElse(null);

        if (existingTitlePlan == null) {
            return false; // 기존 계획이 없으면 false 반환
        }

        // 제목, 시작일, 종료일 업데이트
        existingTitlePlan.setPlanTitle(tourTitleData.getTour_title());
        existingTitlePlan.setTravelStartDay(LocalDate.parse(tourTitleData.getStartDate()));
        existingTitlePlan.setTravelEndDay(LocalDate.parse(tourTitleData.getEndDate()));

        TravelTitlePlanEntity savedata = travelTitlePlanRepository.save(existingTitlePlan);

        if(savedata.getPlanNo() != null) {
            List<TravelDetailPlanEntity> travelDetailPlans = TravelPlanConverter.travelDetailDTOToEntity(tourMemoData, savedata.getPlanNo(), loginUser);
            // TravelDetailPlanEntity 저장
            travelDetailPlanRepository.deleteByPlanNoAndUserId(savedata.getPlanNo(), loginUser);
            travelDetailPlanRepository.saveAll(travelDetailPlans);
            return true; // 성공적으로 저장된 경우 true 반환
        }
        return false; // 다른 경우 false 반환
    }

    public List<TravelTitlePlanDTO> SelectPlanTitle(String sessionId) {
        System.out.println("[PlannerService] SelectPlanTitle");

        return TravelPlanConverter.travelTitlePlansEntityToDTO(travelTitlePlanRepository.findByUserId(sessionId));
    }

    @Transactional
    public void deletePlan(TravelTitlePlanDTO planDTO, String userID) {
        System.out.println("[PlannerService] DeletePlan");
        travelTitlePlanRepository.deleteById(planDTO.getPlanNo());
        travelDetailPlanRepository.deleteByPlanNoAndUserId(planDTO.getPlanNo(), userID);
    }

    public TourApiResponse getTourApiData(String Tour_key , String keyword, int page){
        RestTemplate restTemplate = new RestTemplate();
        TourApiResponse tourApiResponse = new TourApiResponse();
        int numOfRows = 30;

        try {
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            String url = String.format("https://apis.data.go.kr/B551011/KorService1/searchKeyword1?numOfRows=%s&pageNo=%s&MobileOS=ETC&MobileApp=etica&_type=json&listYN=Y&arrange=A&keyword=%s&serviceKey=%s",
                    numOfRows, page, encodedKeyword, Tour_key);
            URI uri = new URI(url);

            // TourApiResponse 객체로 응답 받기
            tourApiResponse = restTemplate.getForObject(uri , TourApiResponse.class);

        } catch (RestClientException e) {
            // 해당 키워드로 불러올 데이터가 없어서 에러가 날때 빈배열로 리턴
            return new TourApiResponse();
        } catch (Exception uriException) {
            System.out.println(uriException.getMessage());
        }
        return tourApiResponse;
    }

    public TourApiResponse getTourData(String data , int page) {
        System.out.println("[plannerService] getTourData");
        int size = 30;
        Pageable pageable = PageRequest.of(page, size);
        Page<TourApiEntity> resultPage = tourApiRepository.findByKeyword("%" + data + "%", pageable);

        // TourApiResponse 객체 생성 및 데이터 설정
        TourApiResponse response = new TourApiResponse();
        TourApiResponse.Response responseBody = new TourApiResponse.Response();
        TourApiResponse.Response.Body body = new TourApiResponse.Response.Body();

        // 총 개수 및 아이템 리스트 설정
        body.setTotalCount((int) resultPage.getTotalElements());

        body.setItems(new TourApiResponse.Response.Body.Items());
        body.getItems().setItem(resultPage.getContent().stream()
                .map(TourApiDTO::new)
                .collect(Collectors.toList()));

        responseBody.setBody(body);
        response.setResponse(responseBody);

        return response;
    }
}
