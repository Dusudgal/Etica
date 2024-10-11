package com.eticaplanner.eticaPlanner.noticeBoard.controller;

import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeRequestDto;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoticeController {

    private final NoticeService memoService;
    private final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    public NoticeController(NoticeService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/memos")
    public ResponseEntity<NoticeResponseDto> createMemo(@RequestBody NoticeRequestDto requestDto) {
        logger.info("Memo 생성 요청: {}", requestDto);

        // 유효성 검사
        if (requestDto.getContents() == null || requestDto.getTitle() == null) {
            logger.warn("잘못된 요청: contents 또는 title이 null입니다.");
            return ResponseEntity.badRequest().body(null);
        }

        // 메모 생성 시 작성자를 "admin"으로 고정하여 서비스 호출
        NoticeResponseDto memoResponse = memoService.createMemo(requestDto.getTitle(), requestDto.getContents());
        logger.info("Memo 생성 성공: {}", memoResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(memoResponse);
    }

    @GetMapping("/memos")
    public ResponseEntity<List<NoticeResponseDto>> getMemos() {
        logger.info("Memo 목록 요청");
        List<NoticeResponseDto> memos = memoService.getMemos();
        logger.info("Memo 목록 반환: {}개", memos.size());
        return ResponseEntity.ok(memos);
    }

    @PutMapping("/memos/{id}")
    public ResponseEntity<Void> updateMemo(@PathVariable Long id, @RequestBody NoticeRequestDto requestDto) {
        logger.info("Memo 업데이트 요청, ID: {}, 내용: {}", id, requestDto);

        // 유효성 검사
        if (requestDto.getContents() == null || requestDto.getTitle() == null) {
            logger.warn("잘못된 요청: contents 또는 title이 null입니다.");
            return ResponseEntity.badRequest().build();
        }

        // 메모 업데이트 시 작성자를 "admin"으로 고정하여 서비스 호출
        Long updatedId = memoService.updateMemo(id, requestDto.getTitle(), requestDto.getContents());
        if (updatedId != null) {
            logger.info("Memo 업데이트 성공, ID: {}", updatedId);
            return ResponseEntity.ok().build();
        }
        logger.warn("업데이트 실패: ID {}에 해당하는 메모가 존재하지 않습니다.", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/memos/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        logger.info("Memo 삭제 요청, ID: {}", id);
        Long deletedId = memoService.deleteMemo(id);

        if (deletedId != null) {
            logger.info("Memo 삭제 성공, ID: {}", deletedId);
            return ResponseEntity.ok().build();
        }
        logger.warn("삭제 실패: ID {}에 해당하는 메모가 존재하지 않습니다.", id);
        return ResponseEntity.notFound().build();
    }
}
