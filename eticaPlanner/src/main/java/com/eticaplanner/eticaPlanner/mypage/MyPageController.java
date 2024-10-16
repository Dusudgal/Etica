package com.eticaplanner.eticaPlanner.mypage;

import com.eticaplanner.eticaPlanner.PlannerPage.dto.TravelTitlePlanDTO;
import com.eticaplanner.eticaPlanner.PlannerPage.service.PlannerService;
import com.eticaplanner.eticaPlanner.SessionDto;
import com.eticaplanner.eticaPlanner.kakao.dto.KakaoUserDto;
import com.eticaplanner.eticaPlanner.user.dto.UserDto;
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
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/MyPage")
public class MyPageController {

    private String nextPage;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyPageService myPageService;

    private final PlannerService plannerService;

    @Autowired
    public MyPageController(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    @GetMapping("/mypage")
    public String mypage(Model model, HttpSession session) {
        // SessionDto를 통해 세션 정보 가져오기
        SessionDto sessionInfo = (SessionDto) session.getAttribute("sessionInfo");

        if (sessionInfo == null || (sessionInfo.getUser_id() == null && sessionInfo.getKakao_id() == null)) {
            return "redirect:/user/sign-in-view";
        }

        String userId;

        if (sessionInfo.getUser_id() != null) {
            UserDto user = myPageService.memberRight(sessionInfo.getUser_id());
            userId = sessionInfo.getUser_id();
            model.addAttribute("user", user);
        } else {
            // 카카오 사용자 정보 처리
            userId = sessionInfo.getKakao_id();
            UserDto user = new UserDto();
            user.setUser_email(sessionInfo.getKakao_email());
            model.addAttribute("user", user);
        }

        List<TravelTitlePlanDTO> plannerList = plannerService.SelectPlanTitle(userId != null ? userId : sessionInfo.getKakao_id());
        model.addAttribute("plannerList", plannerList);

        model.addAttribute("viewName", "MyPage/myPage");
        return "template/layout";
    }

    @PostMapping("/mypage")
    public String changePassword(Model model,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("passwordConfirm") String passwordConfirm,
                                 HttpSession session) {

        SessionDto sessionInfo = (SessionDto) session.getAttribute("sessionInfo");
        if (sessionInfo == null) {
            return "redirect:/user/sign-in-view"; // 세션 정보가 없으면 로그인 페이지로 리다이렉트
        }
        String userId = sessionInfo.getUser_id();

        // 카카오 로그인인 경우 비밀번호 변경을 허용하지 않을 수 있음
        if (userId == null) {
            model.addAttribute("message", "카카오 로그인 사용자는 비밀번호 변경이 불가능합니다.");
        } else {
            boolean changeConfirm = myPageService.changePassword(userId, currentPassword, newPassword, passwordConfirm);
            if (changeConfirm) {
                model.addAttribute("message", "비밀번호 변경에 성공했습니다.");
            } else {
                model.addAttribute("message", "비밀번호 변경에 실패했습니다.");
            }
        }

        model.addAttribute("viewName", "MyPage/myPage");
        return "template/layout";
    }


}
