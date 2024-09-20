package com.eticaplanner.eticaPlanner.PlannerPage.controller;

import com.eticaplanner.eticaPlanner.PlannerPage.dto.PlannerDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelDetailDTO;
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
import java.net.URISyntaxException;
import java.util.HashMap;
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
        mav = new ModelAndView("Planner/PlanSaveSuccessPage");
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

        String loginUser = (String)session.getAttribute("user_id");

        if(loginUser == null || loginUser.isEmpty()){
            return ResponseEntity.ok("login_fail");
        }

        Boolean CreateResult = planService.planCreate(planDto , loginUser);

        System.out.println(CreateResult ? "성공" : "실패");

        if(CreateResult){
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failure");
    }

    @PostMapping("TourApiSearch")
    public ResponseEntity<Map> tourApiSearch(@RequestBody Map<String, String> encodingdata){
        System.out.println("[PlannerController] tourApiSearch");

        RestTemplate restTemplate = new RestTemplate();

        // api 호출시 받아올때 빈맵일수 있으니 초기화를 Map.of()로 한것
        Map response = Map.of();

        String Tour_key = apikeys.tour_apikey();
        int PageNumber = 1;
        int numOfRows = 30;
        String keyword = encodingdata.get("keyword");

        String url = String.format("https://apis.data.go.kr/B551011/KorService1/searchKeyword1?numOfRows=%s&pageNo=%s&MobileOS=ETC&MobileApp=etica&_type=json&listYN=Y&arrange=A&keyword=%s&serviceKey=%s",
                numOfRows, PageNumber , keyword , Tour_key);

        try{
            URI uri = new URI(url);
            System.out.println(uri);
            response = restTemplate.getForObject(uri , Map.class);

        }catch(URISyntaxException uriException){
            System.out.println(uriException.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("ModifyPlan")
    public ModelAndView ModifyPlanner(HttpSession uerSession , TravelTitlePlanDTO planDTO){
        System.out.println("[PlannerController] ModifyPlanner");
        mav = new ModelAndView("Planner/ModifyPlannerPage");
        //SessionDto userInfo = (SessionDto)uerSession.getAttribute("userInfo");

        //PlannerDTO planDetailDTO = planService.SelectPlan(userInfo.getUser_id() , userInfo.getUser_name());
        PlannerDTO planDetailDTO = planService.SelectPlan("dddd" , planDTO.getTour_title());
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

    @GetMapping("PlannerIndex")
    public ModelAndView PlannerIndex(){
        System.out.println("[PlannerController] PlannerIndex");
        mav = new ModelAndView("Planner/PlannerIndex");
        return mav;
    }
}
