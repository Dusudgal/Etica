package com.eticaplanner.eticaPlanner.review.repository;

import com.eticaplanner.eticaPlanner.review.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    List<ReviewEntity> findAllByOrderByReDateDesc();

    List<ReviewEntity> findByUserId(String userId);

    ReviewEntity findByUserIdAndReviewId(String userId, Integer reviewId);

    ReviewEntity findByReviewId(Integer reviewId);

    List<ReviewEntity> findByTourTitle(String tourTitle);

    Page<ReviewEntity> findByUserId(String userId, Pageable pageable);


    Page<ReviewEntity> findByUserIdOrderByReDateDesc(String userId, Pageable pageable);

    Page<ReviewEntity> findByTourTitle(String tourTitle, Pageable pageable);
}
