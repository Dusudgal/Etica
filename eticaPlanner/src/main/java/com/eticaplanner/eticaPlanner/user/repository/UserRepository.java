package com.eticaplanner.eticaPlanner.user.repository;

import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByUserId(String user_id);

    public UserEntity findByUserIdAndUserPassword(String user_id, String user_password);
}
