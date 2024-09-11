package com.eticaplanner.eticaPlanner.mypage.service;

import com.eticaplanner.eticaPlanner.user.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class MyPageDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity isMember(String user_id) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.userId = :userId", UserEntity.class
        );

        query.setParameter("userId", user_id);

        UserEntity memberConfirm = null;
        try {
            memberConfirm = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberConfirm;
    }

}
