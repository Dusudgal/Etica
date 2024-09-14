package com.eticaplanner.eticaPlanner.kakao.controller;


import com.eticaplanner.eticaPlanner.kakao.service.KakaoUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Controller
public class KakaoUserController {

    private final KakaoUserService kakaoUserService;

    @Autowired
    public KakaoUserController(KakaoUserService kakaoUserService) {
        this.kakaoUserService = kakaoUserService;
    }


    // 카카오 로그인 버튼
    @GetMapping("/kakao-sign-in-view")
    public String kakaoSignInView(Model model) {
        // OAuthService에서 카카오 로그인 URL을 생성
        String kakaoLoginUrl = kakaoUserService.getKakaoLoginUrl();
        System.out.println("카카오 로그인 URL: " + kakaoLoginUrl); // URL 로그 출력
        model.addAttribute("kakaoLoginUrl", kakaoLoginUrl);
        // 모델에 사용자 정보와 뷰 이름 추가
        model.addAttribute("viewName", "Kakao/kakaoSignIn");

        return "template/layout";
    }

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
            model.addAttribute("viewName", "Kakao/kakaoSignIn");
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
        model.addAttribute("viewName", "Kakao/kakaoSignIn");
        return "template/layout";
    }

    @GetMapping("/kakao-login")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String kakaoLoginUrl = kakaoUserService.getKakaoLoginUrl();
        response.sendRedirect(kakaoLoginUrl);
    }

    @GetMapping("/oauth/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션 무효화
        return "redirect:/";   // 홈으로 리다이렉트
    }
}