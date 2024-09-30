package com.eticaplanner.eticaPlanner.Admin.repository;

import com.eticaplanner.eticaPlanner.Admin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    public AdminEntity findByAdminId(String AdminId);
    AdminEntity findByAdminIdAndAdminPw(String adminId, String adminPw);
}

