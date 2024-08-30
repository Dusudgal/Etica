package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Planner")
public class PlannerController {
    private ModelAndView mav;
    @GetMapping("PlannerPage")
    public ModelAndView PlannerPage(){
        System.out.println("[PlannerController] PlannerPage");
        mav = new ModelAndView("Planner/PlannerPage");
        return mav;
    }

    @PostMapping("PlannerSaveData")
    public String PlannerSaveData(){
        System.out.println("[PlannerContoller] PlannerSaveData");
        return "Ok" ;
    }
}
