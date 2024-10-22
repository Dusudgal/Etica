package com.eticaplanner.eticaPlanner.PlannerPage.Repository;

import com.eticaplanner.eticaPlanner.Admin.entity.TravelEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TourApiEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourApiRepository extends JpaRepository<TourApiEntity, Long> {

    @Query("SELECT t FROM TourApiEntity t WHERE t.tour_title LIKE %:keyword% OR t.tour_addr LIKE %:keyword%")
    Page<TourApiEntity> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 여행지 이름으로 삭제하는 부분
    @Modifying
    @Transactional
    @Query("DELETE FROM TourApiEntity t WHERE t.id = :id")
    void deleteById(@Param("id") Long id);

    List<TourApiEntity> findByTitleLike(String keyword);
}
