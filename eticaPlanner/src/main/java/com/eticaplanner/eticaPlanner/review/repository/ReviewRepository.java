package com.eticaplanner.eticaPlanner.review.repository;

import com.eticaplanner.eticaPlanner.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eticaplanner.eticaPlanner.review.dto.ReviewDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    List<ReviewEntity> findAllByOrderByReDateDesc();

    List<ReviewEntity> findByUserId(String userId);

    ReviewEntity findByUserIdAndReviewId(String userId, Integer reviewId);

    ReviewEntity findByReviewId(Integer reviewId);

    List<ReviewEntity> findByTourTitle(String tourTitle);
}
