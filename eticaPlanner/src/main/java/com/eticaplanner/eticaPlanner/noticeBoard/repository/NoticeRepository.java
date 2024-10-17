package com.eticaplanner.eticaPlanner.noticeBoard.repository;

import com.eticaplanner.eticaPlanner.noticeBoard.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>  {
    List<Notice> findAll();
    Optional<Notice> findTopByOrderByCreatedAtDesc();

}
