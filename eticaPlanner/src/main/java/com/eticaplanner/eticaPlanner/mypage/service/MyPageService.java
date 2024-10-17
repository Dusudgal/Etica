package com.eticaplanner.eticaPlanner.mypage.service;

import com.eticaplanner.eticaPlanner.common.EncryptUtils;
import com.eticaplanner.eticaPlanner.kakao.dto.KakaoUserDto;
import com.eticaplanner.eticaPlanner.user.dto.UserDto;
import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    final static public int MEMBERRIGHT = 1;
    final static public int MEMBERWRONG = 0;
    @Autowired
    MyPageDao myPageDao;

    @Autowired
    UserRepository userRepository;


    public UserDto memberRight(String user_id) {
        UserDto dto = new UserDto();
        UserEntity entity= userRepository.findByUserId(user_id);
        dto.setUser_id(entity.getUserId());
        dto.setUser_birth(entity.getUserBirth());
        dto.setUser_phone(entity.getUserPhone());
        dto.setUser_name(entity.getUserName());
        return dto;
    }

    public UserEntity findByUserId(String user_id) {
        return userRepository.findByUserId(user_id);
    }

    public boolean changePassword(String user_id, String currentPassword, String newPassword, String passwordConfirm) {
        UserEntity user = userRepository.findByUserId(user_id);

        // 유저가 존재하지 않을때
//        if (user == null) {
//            return false;
//        }

        // 유저의 비밀번호와 현재 비밀번호가 동일하지 않을때
        if(!user.getUserPassword().equals(EncryptUtils.sha256(currentPassword))) {
            return false;
        }

        // 새로운 비밀번호와 비밀번호 확인이 일치하지 않을때
        if (!newPassword.equals(passwordConfirm)) {
            return false;
        }

        user.setUserPassword(EncryptUtils.sha256(newPassword));
        userRepository.save(user);

        return true;
    }

}
