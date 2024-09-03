package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/Admin")
public class AdminController {

    private String nextPage;

    //로그인
    @GetMapping("/signin")
    public String adminsignin() {
        System.out.println("[AdminController]AdminSigninController()");
        return "Admin/signin";
    }

    //로그인 성공,실패
    @PostMapping("/loginconfirm")
    public String adminloginform(){
        System.out.println("[AdminController]AdminloginConfirm()");
        this.nextPage = "Admin/login_ok";
        return this.nextPage;
    }

    //관리자 정보 수정
    @GetMapping("/modifyprofile")
    public String adminmodifyprofile(){
        System.out.println("[AdminController]AdminmodifyProfile()");
        return "Admin/modifyprofile";
    }


}
