package com.eticaplanner.eticaPlanner.PlannerPage.Repository;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelTitlePlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelTitlePlanRepository extends JpaRepository<TravelTitlePlanEntity, Integer> {
    TravelTitlePlanEntity findByUserIdAndPlanTitle(String userId, String planTitle);

    List<TravelTitlePlanEntity> findByUserId(String userId);
}
