package com.eticaplanner.eticaPlanner.user.service;

import com.eticaplanner.eticaPlanner.common.EncryptUtils;
import com.eticaplanner.eticaPlanner.emailVerification.service.EmailVerificationService;
import com.eticaplanner.eticaPlanner.user.dto.UserDto;
import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import com.eticaplanner.eticaPlanner.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailVerificationService emailVerificationService;

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

    // entity를 dto로 변환(필요한 필드:아이디, 닉네임, 핸드폰, 이메일만 변환)
    private UserDto convertToDto(UserEntity userEntity){
        if(userEntity == null){
            return null;
        }
        return UserDto.builder()
                .user_id(userEntity.getUserId())
                .user_nickname(userEntity.getUserNickname())
                .user_phone(userEntity.getUserPhone())
                .user_email(userEntity.getUserEmail())
                .build();
    }

    public Integer addUser(UserDto userDto){

       // 이메일 인증 확인
        boolean isEmailVerified = emailVerificationService.isEmailVerified(Integer.parseInt(userDto.getUser_id()));
        if(!isEmailVerified){
            // 객체의 상태가 호출된 메서드를 수행하기에 적절하지 않을 때 발생시킬 수 있는 예외
            // 예를 들어, 체스 게임을 진행하는데 체스판이 생성되지않은 경우 등
            throw new IllegalStateException("이메일 인증이 필요합니다.");
        }

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
}
