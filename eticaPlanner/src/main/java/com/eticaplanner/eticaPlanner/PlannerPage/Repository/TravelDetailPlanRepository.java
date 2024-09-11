package com.eticaplanner.eticaPlanner.PlannerPage.Repository;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelDetailPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelDetailPlanRepository extends JpaRepository<TravelDetailPlanEntity, Integer> {

}
