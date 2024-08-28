package com.eticaplanner.eticaPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/User")
public class UserController {

    @GetMapping("/sign-in-view")
    public String SignInView(){
        System.out.println("[UserController] index() 테스트");
        return "User/User";
    }
}
