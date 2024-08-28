package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Review")
public class ReviewController {

    @GetMapping("/ReviewIndex")
    public String ReviewIndex(){
        System.out.println("[ReviewController] ReviewIndex()");
        return "Review/ReviewIndex";
    }
}
