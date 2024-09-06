package com.eticaplanner.eticaPlanner.mypage.repository;

import com.eticaplanner.eticaPlanner.mypage.entity.MyPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<MyPageEntity, Integer> {
    public MyPageEntity findByUserId(String userId);
    public MyPageEntity findByUserNo(int userNo);
}
