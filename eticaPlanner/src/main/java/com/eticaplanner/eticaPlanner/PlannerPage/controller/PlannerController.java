package com.eticaplanner.eticaPlanner.PlannerPage.controller;

import com.eticaplanner.eticaPlanner.PlannerPage.dto.*;
import com.eticaplanner.eticaPlanner.PlannerPage.service.PlannerService;
import com.eticaplanner.eticaPlanner.SessionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        if( userSession == null){
            return ResponseEntity.ok("login_fail");
        }

        String userID = userSession.getUser_id() != null ? userSession.getUser_id() : userSession.getKakao_id();

        CreateResult = planService.planCreate( planDto , userID);

        if(CreateResult){
            return ResponseEntity.ok("success");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failure");
    }

    @GetMapping("/TourApiSearch")
    public ResponseEntity<TourApiResponse> tourApiSearch(@RequestParam("keyword") String keyword, @RequestParam("page") int page) {
        System.out.println("[PlannerController] tourApiSearch");

        TourApiResponse tourApiData = planService.getTourData(keyword , page);

        // totalCount가 0이고 items가 null인 경우 두 번째 API 호출
        if (tourApiData.getResponse().getBody().getTotalCount() == 0) {
            TourApiResponse tourApiResponse = planService.getTourApiData(apikeys.tour_apikey(), keyword, page);
            return ResponseEntity.ok(tourApiResponse);
        }

        return ResponseEntity.ok(tourApiData); // 전체 응답 반환
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
