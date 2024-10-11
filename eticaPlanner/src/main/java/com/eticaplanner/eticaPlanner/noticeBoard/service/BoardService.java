package com.eticaplanner.eticaPlanner.noticeBoard.service;

import com.eticaplanner.eticaPlanner.noticeBoard.dto.BoardListResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.BoardRequestDto;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.BoardResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.entity.Board;
import com.eticaplanner.eticaPlanner.noticeBoard.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    @Autowired
    private BoardRepository boardRepository;

    // 게시글 목록 조회 (DTO 반환, 페이지네이션 추가)
    public List<BoardListResponseDto> getBoards(int page, int pageSize) {
        logger.info("Fetching boards for page: {}, pageSize: {}", page, pageSize);
        List<BoardListResponseDto> boards = boardRepository.findAll()
                .stream()
                .skip(page * pageSize) // 페이지 계산
                .limit(pageSize) // 페이지 크기만큼 제한
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
        logger.info("Retrieved {} boards", boards.size());
        return boards;
    }

    // 게시글 수 조회
    public long countBoards() {
        return boardRepository.count();
    }

    // 게시글 상세 조회 (DTO 반환)
    public BoardResponseDto getBoardById(Long id) {
        logger.info("Fetching board with id: {}", id);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Board not found with id: {}", id);
                    return new IllegalArgumentException("해당 게시글이 없습니다. id=" + id);
                });
        logger.info("Retrieved board: {}", board);
        return new BoardResponseDto(board);
    }

    // 게시글 작성 (DTO 사용)
    public Long createBoard(BoardRequestDto requestDto) {
        logger.info("Creating new board with title: {}", requestDto.getTitle());
        Board board = new Board();
        board.setTitle(requestDto.getTitle());
        board.setAuthor(requestDto.getAuthor());
        board.setContent(requestDto.getContent());
        Board savedBoard = boardRepository.save(board);
        logger.info("Created new board with id: {}", savedBoard.getId());
        return savedBoard.getId();
    }

    // 게시글 수정 (DTO 사용)
    public Long updateBoard(Long id, BoardRequestDto requestDto) {
        logger.info("Updating board with id: {}", id);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Board not found with id: {}", id);
                    return new IllegalArgumentException("해당 게시글이 없습니다. id=" + id);
                });

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setAuthor(requestDto.getAuthor());

        Board updatedBoard = boardRepository.save(board);
        logger.info("Updated board with id: {}", updatedBoard.getId());
        return updatedBoard.getId();
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        logger.info("Deleting board with id: {}", id);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Board not found with id: {}", id);
                    return new IllegalArgumentException("해당 게시글이 없습니다. id=" + id);
                });

        boardRepository.delete(board);
        logger.info("Deleted board with id: {}", id);
    }
}
