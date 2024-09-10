package com.eticaplanner.eticaPlanner.user.service;

import com.eticaplanner.eticaPlanner.common.EncryptUtils;
import com.eticaplanner.eticaPlanner.user.dto.UserDto;
import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserEntityByUserId(String user_id){
        return userRepository.findByUserId(user_id);
    }

    public UserEntity getUserEntityByUserNickname(String user_nickname){
        return userRepository.findByUserNickname(user_nickname);
    }

    public UserEntity getUserEntityByUserPhone(String user_phone){
        return userRepository.findByUserPhone(user_phone);
    }

    public Integer addUser(UserDto userDto){
       // 비밀번호 해싱
       String hashed_password = EncryptUtils.sha256(userDto.getUser_password());
       // dto -> entity 변환
       UserEntity userEntity = userRepository.save(
               UserEntity.builder()
                       .userId(userDto.getUser_id())
                       .userPassword(hashed_password) // 해싱된 비밀번호
                       .userName(userDto.getUser_name())
                       .userNickname(userDto.getUser_nickname())
                       .userPhone(userDto.getUser_phone())
                       .userBirth(userDto.getUser_birth())
                       .userGender(userDto.getUser_gender())
                       .userImagePath(userDto.getUser_image_path())
                       .build()
       );

        return userEntity == null ? null : userEntity.getUserNo();
    }

    public UserEntity getUserEntityByUserIdPassword(UserDto userDto){
        // 비밀번호 해싱
        String hashed_password = EncryptUtils.sha256(userDto.getUser_password());
        return userRepository.findByUserIdAndUserPassword(userDto.getUser_id(), hashed_password);
    }
}
