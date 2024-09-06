package com.eticaplanner.eticaPlanner.user;

import com.eticaplanner.eticaPlanner.common.EncryptUtils;
import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 회원가입 API
     * @param user_id
     * @param user_password
     * @param user_name
     * @param user_phone
     * @param user_birth
     * @param user_gender
     * @return
     */
    @PostMapping("/sign-up")
    public Map<String, Object> signUp(
            @RequestParam("user_id") String user_id,
            @RequestParam("user_password") String user_password,
            @RequestParam("user_name") String user_name,
            @RequestParam("user_phone") String user_phone,
            @RequestParam("user_birth") String user_birth,
            @RequestParam("user_gender") String user_gender){

        // password hashing
        String hashed_password = EncryptUtils.sha256(user_password);

        // db insert
        Integer userId = userService.addUser(user_id, hashed_password, user_name, user_phone, user_birth, user_gender);

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
    public Map<String, Object> signIn(
            @RequestParam("user_id") String user_id,
            @RequestParam("user_password") String user_password,
            HttpServletRequest request){

        // 비밀번호 hashing
        String hashed_password = EncryptUtils.sha256(user_password);

        // db조회(userName, 해싱된 비밀번호)
        UserEntity user = userService.getUserEntityByUserIdPassword(user_id, hashed_password);

        // 응답값
        Map<String, Object> result = new HashMap<>();
        if(user != null){ // 성공
            // 로그인 처리
            // 로그인 정보를 세션에 담는다.(사용자 마다)
            HttpSession session = request.getSession();
            session.setAttribute("user_no", user.getUserNo());
            session.setAttribute("user_id", user.getUserId());
            session.setAttribute("user_name", user.getUserName());

            result.put("code", 200);
            result.put("result", "성공");
        } else { // 로그인 불가
            result.put("code", 300);
            result.put("error_message", "존재하지않는 사용자입니다.");
        }
        return result;
    }
}
