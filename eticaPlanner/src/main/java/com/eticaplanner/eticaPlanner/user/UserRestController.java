package com.eticaplanner.eticaPlanner.user;

import com.eticaplanner.eticaPlanner.SessionDto;
import com.eticaplanner.eticaPlanner.common.EncryptUtils;
import com.eticaplanner.eticaPlanner.user.dto.UserDto;
import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    /**
     * 아이디 중복확인 API
     * @param user_id
     * @return
     */
    @RequestMapping("/is-duplicated-id")
    public Map<String, Object> isDuplicatedId(
            @RequestParam("user_id") String user_id) {
        // DB 조회 - SELECT
        UserEntity user = userService.getUserEntityByUserId(user_id);

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

    /**
     * 닉네임 중복확인 API
     * @param user_nickname
     * @return
     */
    @RequestMapping("is-duplicated-nickname")
    public Map<String, Object> isDuplicatedNickName(
            @RequestParam("user_nickname") String user_nickname){

        // DB 조회 - SELECT
        UserEntity user = userService.getUserEntityByUserNickname(user_nickname);

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


    /**
     * 핸드폰 번호 중복확인 API
     * @param user_phone
     * @return
     */
    @RequestMapping("is-duplicated-phone")
    public Map<String, Object> isDuplicatedPhone(
            @RequestParam("user_phone") String user_phone){

        // DB 조회 - SELECT
        UserEntity user = userService.getUserEntityByUserPhone(user_phone);

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

    // 회원가입 API
    @PostMapping("/sign-up")
    public Map<String, Object> signUp(@RequestBody UserDto userDto){
        // password hashing
        // UserService에서 처리
        // UserDto에는 원래 비밀번호가 담기고, UserEntity는 해싱된 비밀번호가 저장됨

        // db insert
        Integer userId = userService.addUser(userDto);
        // 응답값
        Map<String, Object> result = new HashMap<>();
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

        // 비밀번호 hashing
        // 마찬가지로 service에서 처리

        // db조회(user_id, 해싱된 비밀번호)
        UserEntity user = userService.getUserEntityByUserIdPassword(userDto);

        // 응답값
        Map<String, Object> result = new HashMap<>();
        if(user != null){ // 성공
            // 로그인 처리
            // 로그인 정보를 세션에 담는다.(사용자 마다)
            HttpSession session = request.getSession();
            session.setAttribute("user_id", user.getUserId());
            session.setAttribute("user_name", user.getUserName());
            session.setAttribute("user_nickname", user.getUserNickname());

//            SessionDto userSession = new SessionDto();
//            userSession.setUser_name(user.getUserId());
//            userSession.setUser_nickname(user.getUserName());
//            session.setAttribute("userInfo" , userSession );

            result.put("code", 200);
            result.put("result", "성공");
        } else { // 로그인 불가
            result.put("code", 300);
            result.put("error_message", "존재하지않는 사용자입니다.");
        }
        return result;
    }
}
