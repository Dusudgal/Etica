package com.eticaplanner.eticaPlanner.PlannerPage.Repository;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelTitlePlanEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.dto.PlannerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelTitlePlanRepository extends JpaRepository<TravelTitlePlanEntity, Integer> {
    TravelTitlePlanEntity findByUserIdAndPlanTitle(String userId, String planTitle);
}
