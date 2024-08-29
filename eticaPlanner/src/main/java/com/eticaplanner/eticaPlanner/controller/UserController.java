package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/User")
public class UserController {

    /* 기존 로그인 
    @GetMapping("/sign-in-view")
    public String SignInView(){
        return "User/signIn";
    }
    */
    
    // 변경 로그인
    @GetMapping("/sign-in-view")
    public String SignInView(Model model){
        model.addAttribute("viewName", "User/signIn");
        return "template/layout";
    }
    
    // 추후 구현 예정
    // 회원가입
    @GetMapping("/sign-up-view")
    public String SignUpView(){
        return "User/signUp";
    }

    /* 추후 구현 예정
    // 로그아웃
    @GetMapping("/sign-out")
    public String SignOutV(){
        // 세션에 담긴 값 지우고
        // redirect 로그인 화면으로 이동
        return "redirect:/user/sign-in-view";
    }
    */
     
}
