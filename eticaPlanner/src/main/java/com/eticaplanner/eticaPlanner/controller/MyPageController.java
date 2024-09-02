package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/MyPage")
public class MyPageController {

    private String nextPage;

    @GetMapping("/mypage")
    public String mypage(Model model) {
        System.out.println("[MyPageController] mypage");

        model.addAttribute("viewName", "MyPage/myPage");

        this.nextPage = "template/layout";

        return this.nextPage;
    }
//    @GetMapping("/mypage")
//    public String mypage() {
//        this.nextPage = "MyPage/myPage";
//
//        return this.nextPage;
//    }
}
