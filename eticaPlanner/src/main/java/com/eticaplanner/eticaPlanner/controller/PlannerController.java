package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Planner")
public class PlannerController {

    @GetMapping("PlannerPage")
    public String PlannerPage(){
        System.out.println("[PlannerController] PlannerPage");

        return "Planner/PlannerPage";
    }
}
