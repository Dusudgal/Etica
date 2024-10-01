package com.eticaplanner.eticaPlanner.Admin;

import com.eticaplanner.eticaPlanner.Admin.entity.AdminDTo;
import com.eticaplanner.eticaPlanner.Admin.entity.AdminEntity;
import com.eticaplanner.eticaPlanner.Admin.entity.TravelDTO;
import com.eticaplanner.eticaPlanner.Admin.entity.TravelEntity;
import com.eticaplanner.eticaPlanner.Admin.repository.AdminRepository;
import com.eticaplanner.eticaPlanner.Admin.repository.TravelRepository;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TravelRepository travelRepository;

    private AdminDTo ADTO;

    private final int MAX_ATTEMPTS=5;
    private final long LOCK_TIME_DURATION = 30*1000; //30초 단위:밀리초

    public AdminDTo loginConfirm(String admin_id, String admin_pw) {
        System.out.println("[AdminService] loginConfirm()");
        AdminEntity admin = adminRepository.findByAdminIdAndAdminPw(admin_id, admin_pw);

        if(admin == null){
            throw new IllegalArgumentException("Invalid admin ID or password");
        }

        if(admin.isAdminAccountlocked()){
            if (Duration.between(admin.getAdminLocktime(),LocalDateTime.now()).getSeconds() < LOCK_TIME_DURATION){
                throw new AccountLockedException("Account is locked.");
            } else {
                unlockAccount(admin);
            }
        }

        if(!admin.getAdminPw().equals(admin_pw)){
            incrementFailedAttempts(admin);
            throw new IllegalArgumentException("Invalid password");
        }

        resetFailedAttemps(admin);

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

    private void incrementFailedAttempts(AdminEntity admin){
        int newFailAttempts = admin.getAdminLoginattempts() + 1;
        admin.setAdminLoginattempts(newFailAttempts);

        if(newFailAttempts >= MAX_ATTEMPTS){
            lockAccount(admin);
        }
        adminRepository.save(admin);
    }

    private void resetFailedAttemps(AdminEntity admin){
        admin.setAdminLoginattempts(0);
        admin.setAdminAccountlocked(false);
        admin.setAdminLocktime(null);
        adminRepository.save(admin);
    }

    private void lockAccount(AdminEntity admin){
        admin.setAdminAccountlocked(true);
        admin.setAdminLocktime(LocalDateTime.now());
        adminRepository.save(admin);
    }

    private void unlockAccount(AdminEntity admin){
        admin.setAdminAccountlocked(false);
        admin.setAdminLocktime(null);
        admin.setAdminLoginattempts(0);
        adminRepository.save(admin);
    }

    public long getRemainingLockTime(String admin_id){
        AdminEntity admin = adminRepository.findByAdminId(admin_id);
        if(admin != null && admin.isAdminAccountlocked()){
            long lockTimeElapsed = Duration.between(admin.getAdminLocktime(),LocalDateTime.now()).getSeconds();
            return LOCK_TIME_DURATION - lockTimeElapsed;
        }
        return 0;
    }

    // 관리자 정보 수정 메서드 (아이디와 비밀번호만 수정 가능)
    public void updateAdminInfo(AdminDTo adminDto) {
        AdminEntity admin = adminRepository.findByAdminId(adminDto.getAdminId());
        if(admin != null){
            AdminEntity duplicateAdmin = adminRepository.findByAdminId(adminDto.getAdminId());
            if(duplicateAdmin != null && !duplicateAdmin.getAdminId().equals(admin.getAdminId())) {
                throw new IllegalArgumentException("ID is already in use.");
            }

            admin.setAdminId(adminDto.getAdminId());
            if(adminDto.getAdminPw() != null && !adminDto.getAdminPw().isEmpty()){
                admin.setAdminPw(adminDto.getAdminPw());
            }
            adminRepository.save(admin);
        } else {
            throw new IllegalArgumentException("Admin not found");
        }
    }

    // 여행지 데이터베이스에 추가
    public Boolean addTravel(TravelDTO travelDTO) {
        System.out.println("[service] addTravel");
        TravelEntity result = travelRepository.save(new TravelEntity(travelDTO));

        return result != null;
    }

    // 여행지 찾기
    public List<TravelEntity> getAllTravels(){
        return travelRepository.findAll();
    }
}
