package com.eticaplanner.eticaPlanner.kakao.repository;

import com.eticaplanner.eticaPlanner.kakao.entity.KakaoUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUserEntity, Long> {
    Optional<KakaoUserEntity> findByKakaoId(Long kakaoId);
}

