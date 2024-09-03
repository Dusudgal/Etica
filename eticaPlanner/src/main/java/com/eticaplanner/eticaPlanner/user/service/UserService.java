package com.eticaplanner.eticaPlanner.user.service;

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

//    public Integer addUser(String loginId, String password, String name, String phone, String birth, String gender){
//       UserEntity userEntity = userRepository.save(
//               UserEntity.builder()
//                       .userId(loginId)
//                       .userPassword(password)
//                       .userName(name)
//                       .userPhone(phone)
//                       .userBirth(birth)
//                       .userGender(gender)
//                       .build()
//       );
//       return userEntity == null ? null : userEntity.getUserNo();
//    }

//    public UserEntity getUserEntityByUserNo(String user_no){}
}
