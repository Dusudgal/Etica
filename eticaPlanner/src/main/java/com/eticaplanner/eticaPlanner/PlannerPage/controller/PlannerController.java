package com.eticaplanner.eticaPlanner.PlannerPage.controller;

import com.eticaplanner.eticaPlanner.PlannerPage.dto.PlannerDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.service.PlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public PlannerController(PlannerService planService, ApiComponent apikeys) {
        this.planService = planService;
        this.apikeys = apikeys;
    }

    @GetMapping("PlannerPage")
    public ModelAndView plannerPage(){
        System.out.println("[PlannerController] PlannerPage");
        mav = new ModelAndView("Planner/PlannerPage");
        return mav;
    }

    @PostMapping("PlannerSaveData")
    public Map<String , String> plannerSaveData(@RequestBody PlannerDTO planDto){
        System.out.println("[PlannerController] PlannerSaveData");
        PlannerOk = new HashMap<>();

        Boolean CreateResult = planService.planCreate(planDto);

        System.out.println(CreateResult ? "성공" : "실패");
        PlannerOk.put("Message" , "Ok");
        return PlannerOk ;
    }

    public Map<String , String> tour_Apikey(){
        System.out.println("[PlannerController] ApiKeySearch");

        PlannerOk = new HashMap<>();
        PlannerOk.put("Message" , "Ok");
        return PlannerOk;
    }
}
