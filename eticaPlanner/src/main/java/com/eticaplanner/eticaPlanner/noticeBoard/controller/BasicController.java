package com.eticaplanner.eticaPlanner.noticeBoard.controller;

import com.eticaplanner.eticaPlanner.noticeBoard.dto.BoardListResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeListResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.service.BoardService;
import com.eticaplanner.eticaPlanner.noticeBoard.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BasicController {

    @Autowired
    private NoticeService noticeService;

    @Autowired// 변수명 수정: memoService -> noticeService
    private BoardService boardService;

    public BasicController(NoticeService noticeService, BoardService boardService){
        this.noticeService = noticeService;
        this.boardService = boardService;
    }
    // 게시글 목록 페이지
    @GetMapping("/board")
    public String board(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10; // 한 페이지에 보여줄 개  수 설정

        List<NoticeResponseDto> notices = noticeService.getBoards(page, pageSize); // 해당 페이지의 메모 목록 가져오기

        int totalRecords = noticeService.getTotalMemosCount(); // 전체 메모 개수 가져오기
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize); // 전체 페이지 수 계산

        model.addAttribute("notices", notices); // 메모 목록 전달
        model.addAttribute("currentPage", page); // 현재 페이지 전달
        model.addAttribute("totalPages", totalPages); // 전체 페이지 수 전달
        return "Board/board"; // JSP로 이동
    }


    // 메모 목록 페이지 (페이지네이션 제외)
    @GetMapping("/admin/notice")
    public ModelAndView memo(Model model) {
        System.out.println("[AdminController] admin_notice()");
        List<NoticeListResponseDto> notices = noticeService.getAllMemos(); // 메모 목록 가져오기
        model.addAttribute("notices", notices); // 뷰로 메모 전달
        ModelAndView mav = new ModelAndView("template/Adminlayout");
        mav.addObject("viewName","Board/notice");
        return mav; // Board/notice.jsp로 이동
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
