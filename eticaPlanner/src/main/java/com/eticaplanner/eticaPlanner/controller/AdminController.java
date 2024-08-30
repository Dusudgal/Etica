package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/Admin")
public class AdminController {

    //로그인
    @GetMapping("/signin")
    public String adminsignin() {
        System.out.println("[AdminController]AdminSigninController()");
        return "Admin/signin";
    }

}
