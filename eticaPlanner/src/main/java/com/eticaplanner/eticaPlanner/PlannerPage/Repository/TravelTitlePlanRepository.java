package com.eticaplanner.eticaPlanner.PlannerPage.Repository;

import com.eticaplanner.eticaPlanner.PlannerPage.Entity.TravelTitlePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelTitlePlanRepository extends JpaRepository<TravelTitlePlan, Integer> {

}
