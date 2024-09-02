package com.eticaplanner.eticaPlanner.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 회원가입 화면
     * @param model
     * @return
     */
    @GetMapping("/sign-up-view")
    public String SignUpView(Model model){
        model.addAttribute("viewName", "User/signUp");
        return "template/layout";
    }
    
    // 로그인
    @GetMapping("/sign-in-view")
    public String SignInView(Model model){
        model.addAttribute("viewName", "User/signIn");
        return "template/layout";
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
