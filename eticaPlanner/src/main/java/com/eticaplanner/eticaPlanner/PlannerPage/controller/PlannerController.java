package com.eticaplanner.eticaPlanner.PlannerPage.controller;

import com.eticaplanner.eticaPlanner.PlannerPage.dto.PlannerDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelResponseDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelTitlePlanDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.service.PlannerService;
import com.eticaplanner.eticaPlanner.SessionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Planner")
public class PlannerController {

    private ModelAndView mav;
    private final PlannerService planService;
    private ApiComponent apikeys;
    Map<String , String> PlannerOk;

    @Autowired
    public PlannerController(PlannerService planService, ApiComponent apikeys ) {
        this.planService = planService;
        this.apikeys = apikeys;
    }

    @GetMapping("PlannerPage")
    public ModelAndView plannerPage(){
        System.out.println("[PlannerController] PlannerPage");
        mav = new ModelAndView("Planner/PlannerPage");
        String map_key = apikeys.map_apikey();
        mav.addObject("map_key" , map_key);
        return mav;
    }
    @GetMapping("PlannerSaveSuccess")
    public ModelAndView PlannerSaveSuccess(){
        System.out.println("[PlannerController] PlannerSaveSuccess");
        mav = new ModelAndView("template/layout");
        mav.addObject("viewName" , "Planner/PlanSaveSuccessPage");
        return mav;
    }

    @GetMapping("PlannerSavefail")
    public ModelAndView PlannerSaveFail(){
        System.out.println("[PlannerController] PlannerSaveFail");
        mav = new ModelAndView("Planner/PlanSavefail");
        return mav;
    }

    @PostMapping("PlannerSaveData")
    public ResponseEntity<String> plannerSaveData(@RequestBody PlannerDTO planDto , HttpSession session){
        System.out.println("[PlannerController] PlannerSaveData");
        PlannerOk = new HashMap<>();
        Boolean CreateResult = null;

        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");
        String userID = userSession.getUser_id() != null ? userSession.getUser_id() : userSession.getKakao_id();

        if( userSession == null){
            return ResponseEntity.ok("login_fail");
        }

        if(userSession.getUser_id() != null || userSession.getKakao_id() != null){
            CreateResult = planService.planCreate( planDto , userID);
        }

        if(CreateResult){
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failure");
    }

    @PostMapping("TourApiSearch")
    public ResponseEntity<Map> tourApiSearch(@RequestBody Map<String, String> data){
        System.out.println("[PlannerController] tourApiSearch");

        RestTemplate restTemplate = new RestTemplate();

        // api 호출시 받아올때 빈맵일수 있으니 초기화를 Map.of()로 한것
        Map response = Map.of();

        String Tour_key = apikeys.tour_apikey();
        int PageNumber = 1;
        int numOfRows = 30;
        String keyword = data.get("keyword");
        try{
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            String url = String.format("https://apis.data.go.kr/B551011/KorService1/searchKeyword1?numOfRows=%s&pageNo=%s&MobileOS=ETC&MobileApp=etica&_type=json&listYN=Y&arrange=A&keyword=%s&serviceKey=%s",
                    numOfRows, PageNumber , encodedKeyword , Tour_key);
            URI uri = new URI(url);
            response = restTemplate.getForObject(uri , Map.class);
            // API 응답 출력
            System.out.println("API Response: " + response);

            // API 응답에서 body에 접근하는 방법 수정
            Map<String, Object> body = (Map<String, Object>) response.get("response");
            if (body != null) {
                Map<String, Object> items = (Map<String, Object>) body.get("body");
                if (items != null) {
                    Map<String, Object> itemMap = (Map<String, Object>) items.get("items");
                    if (itemMap != null) {
                        Object itemObject = itemMap.get("item");

                        List<Map<String, Object>> itemList = new ArrayList<>();

                        // itemObject가 List일 경우
                        if (itemObject instanceof List) {
                            itemList = (List<Map<String, Object>>) itemObject;
                        } else if (itemObject instanceof Map) {
                            // itemObject가 Map일 경우
                            itemList.add((Map<String, Object>) itemObject);
                        } else {
                            System.out.println("Item is neither List nor Map");
                        }

                        // DTO 리스트를 받아옴
                        List<TravelResponseDTO> travelData = planService.getTravelData(keyword);

                        // TravelResponseDTO를 items에 추가
                        for (TravelResponseDTO dto : travelData) {
                            Map<String, Object> newItemMap = new HashMap<>();
                            newItemMap.put("title", dto.getTitle());
                            newItemMap.put("addr1", dto.getAddr1());
                            newItemMap.put("addr2", dto.getAddr2());
                            newItemMap.put("mapx", dto.getMapx());
                            newItemMap.put("mapy", dto.getMapy());
                            itemList.add(newItemMap);
                        }
                        System.out.println(response);
                    } else {
                        System.out.println("Items is null");
                    }
                } else {
                    System.out.println("Body is null");
                }
            } else {
                System.out.println("Response is null");
            }

        } catch (Exception uriException) {
            System.out.println(uriException.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("SelectPlanTitle")
    public ResponseEntity<List<TravelTitlePlanDTO>> SelectPlanTitle(HttpSession session){
        System.out.println("[PlannerController] SelectPlanTitle");
        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");

        List<TravelTitlePlanDTO> plandto = planService.SelectPlanTitle(userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id() );

        if (plandto != null && !plandto.isEmpty()) {
            return ResponseEntity.ok(plandto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("ModifyPlan")
    public ModelAndView modifyPlanner(HttpSession session , @ModelAttribute TravelTitlePlanDTO planDTO){
        System.out.println("[PlannerController] ModifyPlanner");

        mav = new ModelAndView("Planner/ModifyPlannerPage");

        PlannerDTO planDetailDTO = null;

        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");
        if(userSession == null){
            mav.setViewName("redirect:/user/sign-in-view");
            return mav;
        }
        String userID = userSession.getUser_id() != null ? userSession.getUser_id() : userSession.getKakao_id();

        if(userSession.getUser_id() != null || userSession.getKakao_id() != null){
            planDetailDTO = planService.SelectPlan( userID , planDTO.getTour_title());
        }


        String map_key = apikeys.map_apikey();
        mav.addObject("map_key" , map_key);
        //json 데이터로 넘겨주기 위해 사용하는 objectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String planData = "";
        try {
            planData = objectMapper.writeValueAsString(planDetailDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("planData", planData);

        return mav;
    }

    @PostMapping("ModifyPlanData")
    public ResponseEntity<String> modifyPlanData(@RequestBody PlannerDTO planDto , HttpSession session){
        System.out.println("[PlannerController] modifyPlanData");
        Boolean CreateResult = null;

        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");
        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();

        if(userId != null){
            CreateResult = planService.planModify(planDto , userId);
        }

        System.out.println(CreateResult ? "성공" : "실패");

        if(CreateResult){
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failure");
    }

    @GetMapping("PlannerIndex")
    public ModelAndView plannerIndex(){
        System.out.println("[PlannerController] PlannerIndex");
        mav = new ModelAndView("Planner/PlannerIndex");
        return mav;
    }

    @PostMapping("ViewPlan")
    public ModelAndView plannerView(@ModelAttribute TravelTitlePlanDTO planDTO , HttpSession session){
        System.out.println("[plannerController] PlannerView");

        mav = new ModelAndView("Planner/SelectPlannerPage");
        PlannerDTO planDetailDTO = null;

        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");
        if(userSession == null){
            mav.setViewName("redirect:/user/sign-in-view");
            return mav;
        }
        String userID = userSession.getUser_id() != null ? userSession.getUser_id() : userSession.getKakao_id();

        if(userSession.getUser_id() != null || userSession.getKakao_id() != null){
            planDetailDTO = planService.SelectPlan( userID , planDTO.getTour_title());
        }

        String map_key = apikeys.map_apikey();
        mav.addObject("map_key" , map_key);
        //json 데이터로 넘겨주기 위해 사용하는 objectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String planData = "";
        try {
            planData = objectMapper.writeValueAsString(planDetailDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("planData", planData);

        return mav;
    }

    @PostMapping("DeletePlan")
    public ModelAndView deletePlan(@ModelAttribute TravelTitlePlanDTO planDTO , HttpSession session){
        System.out.println("[PlannerController] detelePlan");
        mav = new ModelAndView();
        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");
        String userID = userSession.getUser_id() != null ? userSession.getUser_id() : userSession.getKakao_id();
        planService.deletePlan(planDTO , userID);
        mav.setViewName("redirect:/MyPage/mypage");
        return mav;
    }

}
