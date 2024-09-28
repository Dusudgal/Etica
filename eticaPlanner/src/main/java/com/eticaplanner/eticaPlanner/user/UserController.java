package com.eticaplanner.eticaPlanner.user;

import com.eticaplanner.eticaPlanner.SessionDto;
import com.eticaplanner.eticaPlanner.kakao.dto.KakaoUserDto;
import com.eticaplanner.eticaPlanner.kakao.service.KakaoUserService;
import com.eticaplanner.eticaPlanner.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("/user")
public class UserController {
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.api.secret}")
    private String kakaoClientSecret;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Value("${logout.redirect.uri}")
    private String logoutRedirectUri;

    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(KakaoUserService kakaoUserService) {
        this.kakaoUserService = kakaoUserService;
    }

    @Autowired
    public UserService userService;

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
     * 아이디 찾기 화면
     * @param model
     * @return
     */
    @GetMapping("/find-id-view")
    public String findIdView(Model model){
        System.out.println("[UserController] FindIdView");
        model.addAttribute("viewName", "User/findId");
        return "template/layout";
    }

    // 비밀번호 찾기 화면
    @GetMapping("/find-password-view")
    public String findPasswordView(Model model){
        System.out.println("[UserController] FindPasswordView");
        model.addAttribute("viewName", "User/findPassword");
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
        model.addAttribute("kakaoApiKey", kakaoApiKey);
        model.addAttribute("kakaoClientSecret", kakaoClientSecret);
        model.addAttribute("kakaoRedirectUri", kakaoRedirectUri);
        model.addAttribute("viewName", "User/signIn");
        return "template/layout";
    }

    /** 카카오 OAuth 인증 콜백 처리 메서드
     *  카카오에서 제공하는 인증 코드를 사용하여 액세스 토큰을 얻고,
     *  이 토큰을 통해 사용자의 정보를 가져온 후,
     *  성공적인 인증 후에는 사용자 정보를 모델에 추가하여 메인 페이지로 리다이렉트
     *  오류가 발생한 경우, 오류 메시지를 모델에 추가하여 로그인 페이지로 돌아간다.
     * @param code
     * @param model
     * @param request
     * @return
     */
    @Description("회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다. " +
            "인가 코드를 이용하여 사용자의 정보를 확인하고 서비스에 회원가입합니다.")
    @GetMapping("/oauth/callback")
    public String handleOAuthCallback(@RequestParam("code") String code, Model model, HttpServletRequest request) {
        System.out.println("카카오에서 받은 인증 코드: " + code);

        try {
            // 인가코드를 이용해 액세스 토큰 가져오기
            String accessToken = kakaoUserService.getAccessToken(code);
            System.out.println("받은 액세스 토큰: " + accessToken);

            // 액세스 토큰을 사용하여 사용자 정보 가져오기
            KakaoUserDto kakaoUser  = kakaoUserService.getUserInfo(accessToken);
            System.out.println("사용자 정보: " + kakaoUser);

            // 카카오 사용자 정보를 세션에 저장
            HttpSession session = request.getSession();
            SessionDto userSession = SessionDto.builder()
                    .kakao_id(kakaoUser.getKakao_id())
                    .kakao_nickname(kakaoUser.getKakao_nickname())
                    .kakao_email(kakaoUser.getKakao_email())
                    .kakao_accessToken(accessToken) // accessToken도 저장
                    .build();

            session.setAttribute("sessionInfo", userSession);

            // 사용자 정보를 모델에 추가하고 성공 페이지로 리다이렉트
            model.addAttribute("kakaoUser", kakaoUser);
            model.addAttribute("viewName", "User/signIn");
            System.out.println("카카오 정보 받아오기 테스트");
            return "redirect:/"; // 성공 페이지로 리다이렉트
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

    // 일반 로그아웃, 카카오 로그아웃 API
    @RequestMapping("/sign-out")
    public String signOut(HttpSession session, HttpServletResponse response, HttpServletRequest request){
        System.out.println("로그아웃 메서드 호출");

        // 로그아웃 리다이렉트 URI 출력
        System.out.println("로그아웃 리다이렉트 URI: " + logoutRedirectUri);


        // 세션에서 사용자 정보 가져오기
        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");
        // 카카오 엑세스 토큰 가져오기(로그인 시 저장된 토큰)
        // NullPointerException 방지
        String kakao_accessToken = userSession != null ? userSession.getKakao_accessToken() : null;
        String kakao_id = userSession != null ? userSession.getKakao_id() : null;

        // kakao_id 출력
        System.out.println("UserController에서 확인: kakao_id: " + kakao_id);

        // 카카오 로그아웃 처리 (카카오 토큰이 있을 때만 실행)
        if (kakao_accessToken != null && kakao_id != null) {
            System.out.println("카카오 로그아웃 API 호출 전");
            kakaoUserService.kakaoLogout(kakao_accessToken);
            System.out.println("카카오 로그아웃 API 호출 후");
        }

        // 세션 무효화
        session.invalidate(); // 모든 속성이 삭제됨, 토큰도 삭제됨
        System.out.println("세션 무효화 완료");

        // 현재 세션 상태 확인
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null) {
            System.out.println("현재 세션이 존재하지 않음 (로그아웃 성공)");
        } else {
            System.out.println("현재 세션이 여전히 존재함 (로그아웃 실패)");
        }

        // JSESSIONID 쿠키 삭제
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/"); // 쿠키 경로 설정
        cookie.setMaxAge(0); // 쿠키 만료 설정
        response.addCookie(cookie); // 응답에 쿠키 추가
        System.out.println("쿠키삭제");

        // 쿠키 상태 확인
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                System.out.println("현재 쿠키: " + c.getName() + " = " + c.getValue());
            }
        } else {
            System.out.println("현재 쿠키가 없습니다.");
        }

        // redirect 로그인 화면으로 이동
        return "redirect:/user/sign-in-view";
    }

    @GetMapping("/logout")
    public String logout(@RequestParam String accessToken) {
        kakaoUserService.kakaoLogout(accessToken);
        return "redirect:" + kakaoUserService.getLogoutRedirectUri();
    }

}
