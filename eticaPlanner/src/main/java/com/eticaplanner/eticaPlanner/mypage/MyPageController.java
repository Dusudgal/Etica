package com.eticaplanner.eticaPlanner.mypage;

import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.repository.UserRepository;
import com.eticaplanner.eticaPlanner.mypage.service.MyPageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/MyPage")
public class MyPageController {

    private String nextPage;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyPageService myPageService;

    @GetMapping("/mypage")
    public String mypage(Model model, HttpSession session) {
        System.out.println("[MyPageController] mypage");
        String userId = (String) session.getAttribute("user_id");
        System.out.println(userId);
        if (userId == null) {
            return "redirect:/user/sign-in-view";
        }

        UserEntity memberResult = myPageService.    memberRight(userId);

        model.addAttribute("userInfo", memberResult);

        UserEntity user = userRepository.findByUserId(userId);

        model.addAttribute("user", user);

        model.addAttribute("viewName", "MyPage/myPage");

        this.nextPage = "template/layout";

        return this.nextPage;
    }

    @PostMapping("/mypage")
    public String changePassword(Model model,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("passwordConfirm") String passwordConfirm,
                                 HttpSession session) {

        String userId = (String) session.getAttribute("user_id");

        boolean changeConfirm = myPageService.changePassword(userId, currentPassword, newPassword, passwordConfirm);

        if (changeConfirm) {
            model.addAttribute("message", "Successful");
        } else {
            model.addAttribute("message", "Fail");
        }

        model.addAttribute("viewName", "MyPage/myPage");

        this.nextPage = "template/layout";
        return this.nextPage;
    }
}
