package com.eticaplanner.eticaPlanner.user;

import com.eticaplanner.eticaPlanner.kakao.service.KakaoUserService;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/user")
public class UserController {

    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(KakaoUserService kakaoUserService) {
        this.kakaoUserService = kakaoUserService;
    }

    /**
     * 일반 회원가입 화면
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
     * 일반 로그인, 카카오 로그인 화면
     * @param model
     * @return
     */
    @GetMapping("/sign-in-view")
    public String signInView(Model model){
        System.out.println("[UserController] SignInView");
        // 카카오 로그인 url 가져와서 모델에 추가
        String kakaoLoginUrl = kakaoUserService.getKakaoLoginUrl();
        model.addAttribute("kakaoLoginUrl", kakaoLoginUrl);
        model.addAttribute("viewName", "User/signIn");
        return "template/layout";
    }

    /**
     * 카카오 OAuth 인증 콜백 처리 메서드
     * 카카오에서 제공하는 인증 코드를 사용하여 액세스 토큰을 얻고,
     * 이 토큰을 통해 사용자의 정보를 가져온 후,
     * 성공적인 인증 후에는 사용자 정보를 모델에 추가하여 메인 페이지로 리다이렉트합니다.
     * 오류가 발생한 경우, 오류 메시지를 모델에 추가하여 로그인 페이지로 돌아갑니다.
     * @param code
     * @param model
     * @return
     */
    @Description("회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다. " +
            "인가 코드를 이용하여 사용자의 정보를 확인하고 서비스에 회원가입합니다.")
    @GetMapping("/oauth/callback")
    public String handleOAuthCallback(@RequestParam("code") String code, Model model) {
        System.out.println("카카오에서 받은 인증 코드: " + code);

        try {
            String accessToken = kakaoUserService.getAccessToken(code);
            System.out.println("받은 액세스 토큰: " + accessToken);

            // 액세스 토큰을 사용하여 사용자 정보 가져오기
            String userInfo = kakaoUserService.getUserInfo(accessToken);
            System.out.println("사용자 정보: " + userInfo);

            // 사용자 정보를 모델에 추가하고 성공 페이지로 리다이렉트
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("viewName", "User/signIn");
            System.out.println("카카오 정보 받아오기 테스트");
            return "redirect:/"; //redirect를 반드시 해야한다...! 가 아니라.. 보내줄 view이름을 적어야한다.
        } catch (HttpClientErrorException e) {
            // HTTP 요청 중 발생한 오류 처리
            System.out.println("HTTP 오류 발생: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            model.addAttribute("errorMessage", "카카오 서버와의 통신에 문제가 발생했습니다. 다시 시도해주세요.");
        } catch (Exception e) {
            // 기타 일반적인 오류 처리
            System.out.println("OAuth 인증 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "로그인에 실패하였습니다. 다시 시도해주세요.");
        }
        // 오류 발생 시 로그인 페이지로 리턴
        model.addAttribute("viewName", "User/signIn");
        return "template/layout";
    }

    /**
     * 일반 로그아웃 API
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
