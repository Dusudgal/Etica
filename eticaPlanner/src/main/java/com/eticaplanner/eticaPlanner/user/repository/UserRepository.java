package com.eticaplanner.eticaPlanner.user.repository;

import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByUserId(String user_id);
    public UserEntity findByUserNickname(String user_nickname);
    public UserEntity findByUserPhone(String user_phone);
    public UserEntity findByUserEmail(String user_email);
    public UserEntity findByUserIdAndUserPassword(String user_id, String user_password);
}
