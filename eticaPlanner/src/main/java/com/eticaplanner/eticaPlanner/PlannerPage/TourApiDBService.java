package com.eticaplanner.eticaPlanner.PlannerPage;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TourApiEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Repository.TourApiRepository;
import com.eticaplanner.eticaPlanner.PlannerPage.controller.ApiComponent;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TourApiDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TourApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TourApiDBService {

    private final ApiComponent apikey;
    private final TourApiRepository tourApiRepository;
    //중복 키워드를 문제 없이 처리하기 위해서 만든 변수
    private final ConcurrentHashMap<String, Lock> keywordLocks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Boolean> processedKeywords = new ConcurrentHashMap<>();

    public TourApiDBService(ApiComponent apikey , TourApiRepository tourApiRepository) {
        this.apikey = apikey;
        this.tourApiRepository = tourApiRepository;
    }

    public synchronized void setServerUpdateData(){
        String[] searchKeywords = new String[]{"서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"};

        // 이미 처리된 키워드와 새로운 키워드를 합쳐서 데이터 업데이트
        Set<String> allKeywords = ConcurrentHashMap.newKeySet();
        allKeywords.addAll(processedKeywords.keySet());
        Collections.addAll(allKeywords, searchKeywords);

        setTourData(allKeywords.toArray(new String[0]));
    }

    public void setNewKeywordData(String newKeyword){
        Lock lock = keywordLocks.computeIfAbsent(newKeyword, k -> new ReentrantLock());
        // 해당 키워드를 다른 스레드에서 접근할수 없도록 락 설정
        lock.lock();
        try {
            // 이미 처리 중인 키워드인지 체크 / 키워드 없을시 null 반환
            if (processedKeywords.putIfAbsent(newKeyword, true) != null) {
                return;
            }
            setTourData(new String[]{newKeyword});
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public void setTourData(String[] SearchKeyword) {
        System.out.println("관광지 데이터 업데이트 시작");
        RestTemplate restTemplate = new RestTemplate();

        String Tour_key = apikey.tour_apikey();
        List<TourApiEntity> existingTourData = tourApiRepository.findAll();

        // 기존 데이터를 불러와서 불러오는 데이터가 DB에 존재하는지 확인하기 위한 변수
        Map<String, TourApiEntity> existingDataMap = existingTourData.stream()
                .collect(Collectors.toMap(
                        e -> e.getTour_title() + "_" + e.getTour_addr(),
                        Function.identity(),
                        (existing, replacement) -> existing
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

                    StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/searchKeyword1?");
                    urlBuilder.append(String.format("numOfRows=%s", numOfRows))
                            .append("&")
                            .append(String.format("pageNo=%s", pageNo))
                            .append("&MobileOS=ETC")
                            .append("&MobileApp=etica")
                            .append("&_type=json")
                            .append("&listYN=Y")
                            .append("&arrange=A")
                            .append(String.format("&keyword=%s", encodedKeyword))
                            .append("&serviceKey=")
                            .append(Tour_key);

                    String url = urlBuilder.toString();
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
