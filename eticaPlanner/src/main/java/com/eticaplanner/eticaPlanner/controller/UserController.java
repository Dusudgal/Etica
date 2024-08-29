package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/User")
public class UserController {

    // 로그인
    @GetMapping("/sign-in-view")
    public String SignInView(){
        return "User/signIn";
    }

    // 회원가입
    @GetMapping("/sign-up-view")
    public String SignUpView(){
        return "User/signUp";
    }
}
