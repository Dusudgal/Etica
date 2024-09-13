package com.eticaplanner.eticaPlanner.user;

import jakarta.servlet.http.HttpSession;
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
    public String signUpView(Model model){
        System.out.println("[UserController] SignUpView");
        model.addAttribute("viewName", "User/signUp");
        return "template/layout";
    }

    /**
     * 로그인 화면
     * @param model
     * @return
     */
    @GetMapping("/sign-in-view")
    public String signInView(Model model){
        System.out.println("[UserController] SignInView");
        model.addAttribute("viewName", "User/signIn");
        return "template/layout";
    }

    /**
     * 로그아웃 API
     * @param session
     * @return
     */
    @RequestMapping("/sign-out")
    public String signOut(HttpSession session){
        // 세션에 담긴 값 지우고
        session.removeAttribute("user_id");
        session.removeAttribute("user_name");
        session.removeAttribute("user_nickname");
        // redirect 로그인 화면으로 이동
        return "redirect:/user/sign-in-view";
    }

}
