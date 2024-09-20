package com.eticaplanner.eticaPlanner.PlannerPage.Repository;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelDetailPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelDetailPlanRepository extends JpaRepository<TravelDetailPlanEntity, Integer> {
    List<TravelDetailPlanEntity> findByPlanNoAndUserId(Integer planNo , String userId);
}
