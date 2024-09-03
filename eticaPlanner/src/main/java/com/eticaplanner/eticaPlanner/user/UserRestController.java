package com.eticaplanner.eticaPlanner.user;

import com.eticaplanner.eticaPlanner.common.EncryptUtils;
import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.service.UserService;
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
            @RequestParam String user_id) {
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
     *  회원가입 API
     * @param loginId
     * @param password
     * @param name
     * @param phone
     * @param birth
     * @param gender
     * @return
     */
    @PostMapping("/sign-up")
    public Map<String, Object> signUp(
            @RequestParam String loginId,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String birth,
            @RequestParam String gender){

        // password hashing
        String hashedPassword = EncryptUtils.sha256(password);

        // db insert
        Integer userId = userService.addUser(loginId, hashedPassword, name, phone, birth, gender);

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

    @PostMapping("/sign-in")
    public Map<String, Object> signIn(
            @RequestParam("userName") String userName,
            @RequestParam("userPassword") String userPassword){

        // 비밀번호 hashing
        String hashedPassword = EncryptUtils.sha256(userPassword);
        // db조회(userName, 해싱된 비밀번호)
        
        // 응답값
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("result", "성공");
        return result;
    }
}
