package com.eticaplanner.eticaPlanner.Admin.repository;

import com.eticaplanner.eticaPlanner.Admin.entity.TravelEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<TravelEntity, Long> {
    List<TravelEntity> findAll();
    // 여행지 이름으로 삭제하는 메서드
    @Modifying
    @Transactional
    @Query("DELETE FROM TravelEntity t WHERE t.travelName = :travelName")
    void deleteByTravelName(@Param("travelName") String travelName);


    List<TravelEntity> findByTravelNameLike(String keyword);
}
