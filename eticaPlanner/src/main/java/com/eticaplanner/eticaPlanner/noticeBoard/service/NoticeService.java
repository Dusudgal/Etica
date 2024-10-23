package com.eticaplanner.eticaPlanner.noticeBoard.service;

import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeListResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.entity.Notice;
import com.eticaplanner.eticaPlanner.noticeBoard.repository.NoticeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    private final Logger logger = LoggerFactory.getLogger(NoticeService.class);
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 모든 메모를 조회하는 메서드
    public List<NoticeResponseDto> getMemos() {
        List<Notice> memos = noticeRepository.findAll();
        return memos.stream()
                .map(NoticeResponseDto::new)
                .collect(Collectors.toList());
    }

    // 페이지네이션된 게시글 조회
    public List<NoticeResponseDto> getBoards(int page, int pageSize) {
        logger.info("Fetching boards for page: {}, pageSize: {}", page, pageSize);

        return noticeRepository.findAll(PageRequest.of(page, pageSize))
                .stream()
                .map(NoticeResponseDto::new)
                .collect(Collectors.toList());
    }

    // 전체 메모 개수 반환
    public int getTotalMemosCount() {
        return (int) noticeRepository.count();
    }

    // 모든 메모를 조회하는 메서드
    public List<NoticeListResponseDto> getAllMemos() {
        List<Notice> memos = noticeRepository.findAll();
        return memos.stream()
                .map(NoticeListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글 생성
    public NoticeResponseDto createMemo(String adminId, String title, String contents) {
        Notice memo = new Notice(adminId, contents, title);
        Notice savedMemo = noticeRepository.save(memo);
        return new NoticeResponseDto(savedMemo);
    }

    // 특정 ID로 메모 조회
    public NoticeResponseDto getMemoById(Long id) {
        Notice memo = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 메모가 없습니다."));
        return new NoticeResponseDto(memo);
    }

    // 메모 수정
    public Long updateMemo(Long id, String title, String contents) {
        Notice memo = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 메모가 없습니다."));
        memo.setTitle(title);
        memo.setContents(contents);
        memo.setCreatedAt(LocalDateTime.now());
        noticeRepository.save(memo);
        return memo.getId();
    }

    // 메모 삭제
    public Long deleteMemo(Long id) {
        Notice memo = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 메모가 없습니다."));
        noticeRepository.delete(memo);
        return memo.getId();
    }

    // 최신 공지사항 가져오기 (Optional로 수정)
    public Optional<NoticeResponseDto> getLatestNotice() {
        return noticeRepository.findTopByOrderByCreatedAtDesc()
                .map(NoticeResponseDto::new);
    }
}
