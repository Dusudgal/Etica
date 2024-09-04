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

    public Integer addUser(String user_id, String user_password, String user_name, String user_phone, String user_birth, String user_gender){
       UserEntity userEntity = userRepository.save(
               UserEntity.builder()
                       .userId(user_id)
                       .userPassword(user_password)
                       .userName(user_name)
                       .userPhone(user_phone)
                       .userBirth(user_birth)
                       .userGender(user_gender)
                       .build()
       );
       return userEntity == null ? null : userEntity.getUserNo();
    }

    public UserEntity getUserEntityByUserIdPassword(String user_id, String user_password){
        return userRepository.findByUserIdAndUserPassword(user_id, user_password);
    }
}
