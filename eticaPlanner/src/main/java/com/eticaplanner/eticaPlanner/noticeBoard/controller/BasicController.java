package com.eticaplanner.eticaPlanner.noticeBoard.controller;

import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeListResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BasicController {

    @Autowired
    private NoticeService noticeService; // 변수명 수정: memoService -> noticeService

    // 게시글 목록 페이지
    @GetMapping("/board")
    public String board(Model model) {
        List<NoticeListResponseDto> notices = noticeService.getAllMemos(); // 메모 목록 가져오기
        model.addAttribute("notices", notices); // 뷰로 메모 전달
        return "Board/board"; // Board/board.jsp로 이동
    }

    // 메모 목록 페이지 (페이지네이션 제외)
    @GetMapping("/board/notice")
    public String memo(Model model) {
        List<NoticeListResponseDto> notices = noticeService.getAllMemos(); // 메모 목록 가져오기
        model.addAttribute("notices", notices); // 뷰로 메모 전달
        return "Board/notice"; // Board/notice.jsp로 이동
    }

    // 메모 상세보기 페이지
    @GetMapping("/board/notice/{id}")
    public String memoDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            NoticeResponseDto memo = noticeService.getMemoById(id); // 메모 상세 조회 (메서드 이름 수정)
            model.addAttribute("memo", memo); // 뷰로 메모 전달
            return "Board/noticeDetail"; // Board/noticeDetail.jsp로 이동
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/board/notice"; // 게시글 목록 페이지로 리다이렉트
        }
    }
}
