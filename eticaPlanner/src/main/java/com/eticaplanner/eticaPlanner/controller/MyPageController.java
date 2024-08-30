package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/MyPage")
public class MyPageController {

    private String nextPage;

    @GetMapping("/mypage")
    public String mypage() {
        System.out.println("[MyPageController] mypage");

        this.nextPage = "MyPage/myPage";

        return this.nextPage;
    }
}
