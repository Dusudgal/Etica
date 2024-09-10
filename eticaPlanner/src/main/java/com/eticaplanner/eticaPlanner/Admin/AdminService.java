package com.eticaplanner.eticaPlanner.Admin;

import com.eticaplanner.eticaPlanner.Admin.entity.AdminDTo;
import com.eticaplanner.eticaPlanner.Admin.entity.AdminEntity;
import com.eticaplanner.eticaPlanner.Admin.repository.AdminRepository;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    private AdminRepository entityManager;
    private AdminDTo ADTO;

    public AdminDTo loginConfirm(String admin_id, String admin_pw) {
        System.out.println("[AdminService] loginConfirm()");
        AdminEntity admin = entityManager.findByAdminIdAndAdminPw(admin_id, admin_pw);

        if(admin == null){
            throw new IllegalArgumentException("Invalid admin ID or password");
        }

        if(ADTO == null){
            ADTO = new AdminDTo();
        }
        ADTO.setAdminId(admin.getAdminId());
        ADTO.setAdminName(admin.getAdminName());
        ADTO.setAdminPhone(admin.getAdminPhone());
        ADTO.setAdminEmail(admin.getAdminEmail());
        System.out.println(ADTO.getAdminId());
        return ADTO;
    }

    public void updateAdminInfo(AdminDTo adminDto) {
        AdminEntity admin = adminRepository.findByAdminId(adminDto.getAdminId());
        if(admin != null){
            admin.setAdminName(adminDto.getAdminName());
            admin.setAdminPhone(adminDto.getAdminPhone());
            admin.setAdminEmail(adminDto.getAdminEmail());
            if (adminDto.getAdminPw() != null && !adminDto.getAdminPw().isEmpty()){
                admin.setAdminPw(adminDto.getAdminPw());
            }
            adminRepository.save(admin);
        } else {
            throw new IllegalArgumentException("Admin not found");
        }
    }

}
