package com.eticaplanner.eticaPlanner.PlannerPage.Repository;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TourApiEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TourApiRepository extends JpaRepository<TourApiEntity, Integer> {

    @Query("SELECT t FROM TourApiEntity t WHERE t.title LIKE %:keyword% OR t.addr LIKE %:keyword%")
    Page<TourApiEntity> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
