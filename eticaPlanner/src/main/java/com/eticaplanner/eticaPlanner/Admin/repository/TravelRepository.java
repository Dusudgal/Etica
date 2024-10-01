package com.eticaplanner.eticaPlanner.Admin.repository;

import com.eticaplanner.eticaPlanner.Admin.entity.TravelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<TravelEntity, Long> {
    List<TravelEntity> findAll();
}
