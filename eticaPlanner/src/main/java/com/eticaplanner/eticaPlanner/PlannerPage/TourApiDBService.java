package com.eticaplanner.eticaPlanner.PlannerPage;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TourApiEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TourApiRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.controller.ApiComponent;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TourApiDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TourApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TourApiDBService {

    private final ApiComponent apikey;
    private final TourApiRepository tourApiRepository;

    public TourApiDBService(ApiComponent apikey , TourApiRepository tourApiRepository) {
        this.apikey = apikey;
        this.tourApiRepository = tourApiRepository;
    }
    public void setServerData(){
        String[] SearchKeyword = new String[]{"서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"};
        setTourData(SearchKeyword);
    }
    public void setNewKeywordData(String newKeyword){
        setTourData(new String[]{newKeyword});
    }

    public void setTourData(String[] SearchKeyword) {
        System.out.println("관광지 데이터 업데이트 시작");
        RestTemplate restTemplate = new RestTemplate();

        String Tour_key = apikey.tour_apikey();
        List<TourApiEntity> existingTourData = tourApiRepository.findAll();

        // 기존 데이터의 고유 키 맵 생성
        Map<String, TourApiEntity> existingDataMap = existingTourData.stream()
                .collect(Collectors.toMap(
                        e -> e.getTour_title() + "_" + e.getTour_addr(), // 고유 키 생성
                        Function.identity(),
                        (existing, replacement) -> existing // 기존 값 유지
                ));

        int numOfRows = 30;
        for (String keyword : SearchKeyword) {
            int pageNo = 1;
            int totalCount = 0;

            try {
                System.out.println(keyword + " 시작");
                Thread.sleep(1000); // API 호출 사이 대기
            } catch (Exception e) {
                System.out.println(e);
            }

            List<TourApiEntity> TourData = new ArrayList<>();

            do {
                try {
                    String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
                    String url = String.format("https://apis.data.go.kr/B551011/KorService1/searchKeyword1?numOfRows=%s&pageNo=%s&MobileOS=ETC&MobileApp=etica&_type=json&listYN=Y&arrange=D&keyword=%s&serviceKey=%s",
                            numOfRows, pageNo, encodedKeyword, Tour_key);
                    URI uri = new URI(url);

                    TourApiResponse response = restTemplate.getForObject(uri, TourApiResponse.class);

                    // 응답의 헤더가 성공인지 체크
                    if (response == null || response.getResponse() == null || response.getResponse().getHeader() == null) {
                        System.out.println("Error: Response is null");
                        break; // 응답 헤더 , response가 null인 경우 반복 종료
                    }

                    String resultCode = response.getResponse().getHeader().getResultCode();
                    totalCount = response.getResponse().getBody().getTotalCount();
                    List<TourApiDTO> items = response.getResponse().getBody().getItems().getItem();

                    // items 처리 로직 추가
                    if (items == null || items.isEmpty()) {
                        System.out.println("Warning: No items found for keyword: " + keyword);
                        break; // items가 null이거나 비어있는 경우 반복 종료
                    }

                    for (TourApiDTO item : items) {
                        TourApiEntity tourEntity = new TourApiEntity(item);

                        // 고유 키 생성
                        String uniqueKey = tourEntity.getTour_title() + "_" + tourEntity.getTour_addr();
                        TourApiEntity existingEntity = existingDataMap.get(uniqueKey);

                        if (existingEntity != null) {
                            // 기존 데이터가 있다면 업데이트
                            existingEntity.updateFrom(tourEntity);
                            TourData.add(existingEntity);
                        } else {
                            // 데이터가 없다면 새로 추가
                            TourData.add(tourEntity);
                        }
                    }
                    // 페이지 단위로 저장
                    tourApiRepository.saveAll(TourData);
                    tourApiRepository.flush(); // 데이터베이스에 반영

                    TourData.clear(); // 다음 페이지를 위해 초기화
                    pageNo++;
                } catch (Exception uriException) {
                    System.out.println(uriException.getMessage());
                    break; // 에러 발생 시 반복 종료
                }
            } while (pageNo <= (totalCount + numOfRows - 1) / numOfRows); // 남은 페이지 수 계산
        }

        System.out.println("관광지 데이터 업데이트 완료");

    }
}
