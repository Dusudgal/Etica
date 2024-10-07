package com.eticaplanner.eticaPlanner.noticeBoard.repository;

import com.eticaplanner.eticaPlanner.noticeBoard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
