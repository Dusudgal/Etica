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

    public UserDto  getUserDtoByUserId(String user_id){
        return convertToDto(userRepository.findByUserId(user_id));
    }

    public UserDto  getUserDtoByUserNickname(String user_nickname){
        return convertToDto(userRepository.findByUserNickname(user_nickname));
    }

    public UserDto  getUserDtoByUserPhone(String user_phone){
        return convertToDto(userRepository.findByUserPhone(user_phone));
    }

    public UserDto  getUserDtoByUserEmail(String user_email){
        return convertToDto(userRepository.findByUserEmail(user_email));
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
                       .userEmail(userDto.getUser_email())
                       .build()
       );

        return userEntity == null ? null : userEntity.getUserNo();
    }

    public UserDto getUserDtoByUserIdPassword(UserDto userDto){
        // 비밀번호 해싱
        String hashed_password = EncryptUtils.sha256(userDto.getUser_password());
        return convertToDto(userRepository.findByUserIdAndUserPassword(userDto.getUser_id(), hashed_password));
    }

    public UserDto findUserIdByEmail(String user_email) {
        UserEntity userEntity = userRepository.findByUserEmail(user_email);
        return userEntity != null ? convertToDto(userEntity) : null;
    }

    private UserDto convertToDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return UserDto.builder()
                .user_id(userEntity.getUserId())
                .user_nickname(userEntity.getUserNickname())
                .user_phone(userEntity.getUserPhone())
                .user_email(userEntity.getUserEmail())
                .build();
    }
}
