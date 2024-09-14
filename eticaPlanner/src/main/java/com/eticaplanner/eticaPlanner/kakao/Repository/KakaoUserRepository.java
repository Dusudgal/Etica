package com.eticaplanner.eticaPlanner.kakao.Repository;

import com.eticaplanner.eticaPlanner.kakao.Entity.KakaoUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUserEntity, Long> {
    Optional<KakaoUserEntity> findByKakaoId(Long kakaoId);
}

