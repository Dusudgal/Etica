package com.eticaplanner.eticaPlanner.user;

import com.eticaplanner.eticaPlanner.SessionDto;

import com.eticaplanner.eticaPlanner.common.EncryptUtils;
import com.eticaplanner.eticaPlanner.emailVerification.service.EmailVerificationService;

import com.eticaplanner.eticaPlanner.kakao.service.KakaoUserService;
import com.eticaplanner.eticaPlanner.user.dto.UserDto;
import com.eticaplanner.eticaPlanner.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private KakaoUserService kakaoUserService;


    @Autowired
    private EmailVerificationService emailVerificationSerive;

    /**
     * 아이디 중복확인 API
     * @param user_id
     * @return
     */

    @RequestMapping("/is-duplicated-id")
    public Map<String, Object> isDuplicatedId(
            @RequestParam("user_id") String user_id) {
        // DB 조회 - SELECT
        UserDto user = userService.getUserDtoByUserId(user_id);

        Map<String, Object> result = new HashMap<>();
        if(user != null){ // 중복
            result.put("code", 200);
            result.put("is_duplicated_id", true);
        } else { // 중복x
            result.put("code", 200);
            result.put("is_duplicated_id", false);
        }
        return result;
    }

    // 닉네임 중복확인 API
    @RequestMapping("is-duplicated-nickname")
    public Map<String, Object> isDuplicatedNickName(
            @RequestParam("user_nickname") String user_nickname){

        // DB 조회 - SELECT
        UserDto user = userService.getUserDtoByUserNickname(user_nickname);

        Map<String, Object> result = new HashMap<>();
        if(user != null){
            result.put("code", 200);
            result.put("is_duplicated_nickname", true);
        } else {
            result.put("code", 200);
            result.put("is_duplicated_nickname", false);
        }
        return result;
    }


    // 핸드폰 번호 중복확인 API
    @RequestMapping("is-duplicated-phone")
    public Map<String, Object> isDuplicatedPhone(
            @RequestParam("user_phone") String user_phone){

        // DB 조회 - SELECT
        UserDto user = userService.getUserDtoByUserPhone(user_phone);

        Map<String, Object> result = new HashMap<>();
        if(user != null){
            result.put("code", 200);
            result.put("is_duplicated_phone", true);
        } else {
            result.put("code", 200);
            result.put("is_duplicated_phone", false);
        }
        return result;
    }


    /**
     * 이메일 중복확인
     * @param user_email
     * @return
     */
    @RequestMapping("is-duplicated-email")
    public Map<String, Object> isDuplicatedEmail(
            @RequestParam("user_email") String user_email){

        // DB 조회 - SELECT
        UserDto user = userService.getUserDtoByUserEmail(user_email);

        Map<String, Object> result = new HashMap<>();
        if(user != null){
            result.put("code", 200);
            result.put("is_duplicated_email", true);
        } else {
            result.put("code", 200);
            result.put("is_duplicated_email", false);
        }
        return result;
    }


    /**
     * 회원가입 API
     * @param userDto
     * @return
     */
    @PostMapping("/sign-up")
    public Map<String, Object> signUp(@RequestBody UserDto userDto){

        Map<String, Object> result = new HashMap<>();

        /*
        // 이메일 인증 확인                                                          //여기서 정수로 변환하면 회원가입때 에러남
        boolean is_email_verified = emailVerificationSerive.isEmailVerified(Integer.parseInt(userDto.getUser_email()));
        if(!is_email_verified){ // 인증이 안됐다면
            result.put("code", 400);
            result.put("error_message", "이메일 인증이 필요합니다. 이메일을 확인하세요");
            return result;
        }*/


        // password hashing
        // UserService에서 처리
        // UserDto에는 원래 비밀번호가 담기고, UserEntity는 해싱된 비밀번호가 저장됨
        // db insert
        Integer userId = userService.addUser(userDto);

        // 응답값
        if(userId != null){
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "회원가입에 실패했습니다.");
        }

        return result;
    }

    // 로그인 API
    @PostMapping("/sign-in")
    public Map<String, Object> signIn(@RequestBody UserDto userDto, HttpServletRequest request){
        System.out.println("[UserRestController] signIn 로그인을 하는 화면");
        System.out.println("userDto.getUser_id() + userDto.getUser_password() = " + userDto.getUser_id() + userDto.getUser_password());
        // 비밀번호 hashing은 마찬가지로 service에서 처리

        // db조회(user_id, 해싱된 비밀번호)
        SessionDto userSession = userService.getUserDtoByUserIdPassword(userDto);

        // 응답값
        Map<String, Object> result = new HashMap<>();
        if(userSession != null) {
            if (userSession.getUser_id() != null && userSession.getUser_nickname() != null) { // 성공

                // 세션에 저장
                HttpSession session = request.getSession();
                session.setAttribute("sessionInfo", userSession);
                result.put("code", 200);
                result.put("result", "성공"); // 아이디, 비밀번호 모두 맞음
            } else {
                result.put("code", 401);
                result.put("result", "비밀번호가 틀렸습니다"); // 아이디만 맞고 비밀번호가 틀림
            }
        } else {
            result.put("code", 404);
            result.put("result", "아이디가 존재하지 않습니다."); // 다 틀림
        }
        return result;
    }

    /**
     * 카카오 로그인 URL을 반환하는 API
     * @return
     */
    @GetMapping("/kakao-login")
    public Map<String, String> getKakaoLoginUrl() {
        String kakaoLoginUrl = kakaoUserService.getKakaoLoginUrl();
        Map<String, String> response = new HashMap<>();
        response.put("kakaoLoginUrl", kakaoLoginUrl);
        return response;
    }

    /**
     * 아이디 찾기 API
     * @param email
     * @return
     */
    @GetMapping("/find-id")
    public Map<String, Object> findId(@RequestParam String email) {
        UserDto userDto = userService.findUserIdByEmail(email);
        Map<String, Object> result = new HashMap<>();

        if (userDto != null) {
            result.put("code", 200);
            result.put("user_id", userDto.getUser_id());
        } else {
            result.put("code", 400);
            result.put("error_message", "해당 이메일로 등록된 아이디가 없습니다.");
        }

        return result; // Map을 반환
    }

}
