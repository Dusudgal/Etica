package com.eticaplanner.eticaPlanner.user.service;

import com.eticaplanner.eticaPlanner.SessionDto;
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

    public UserDto getUserDtoByUserId(String user_id){
        return convertToDto(userRepository.findByUserId(user_id));
    }

    public UserDto getUserDtoByUserNickname(String user_nickname){
        return convertToDto(userRepository.findByUserNickname(user_nickname));
    }

    public UserDto getUserDtoByUserPhone(String user_phone){
        return convertToDto(userRepository.findByUserPhone(user_phone));
    }

    public UserDto getUserDtoByUserEmail(String user_email){
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

    public SessionDto getUserDtoByUserIdPassword(UserDto userDto){

        // 비밀번호 해싱
        UserDto resultDto = convertToDto(userRepository.findByUserId(userDto.getUser_id()));
        if(resultDto != null) {
            if(resultDto.getUser_password().equals(EncryptUtils.sha256(userDto.getUser_password()))){
                System.out.println("resultDto.getUser_password() = " + resultDto.getUser_password());
                System.out.println("아이디, 비밀번호가 일치합니다.");
                return SessionDto.builder()
                        .user_id(resultDto.getUser_id())
                        .user_name(resultDto.getUser_name())
                        .user_nickname(resultDto.getUser_nickname())
                        .build(); // 아이디, 비번 맞음
            } else {
                // 아이디만 맞을 때
                System.out.println("비밀번호가 불일치합니다.");
                return SessionDto.builder()
                        .user_id(resultDto.getUser_id())
                        .build(); // 아이디만 맞음
            }
        } else {
            // 아이디 틀림
            System.out.println("아이디가 존재하지않습니다.");
            return null;
        }
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
                .user_password(userEntity.getUserPassword())
                .user_phone(userEntity.getUserPhone())
                .user_email(userEntity.getUserEmail())
                .build();
    }

}
