package com.eticaplanner.eticaPlanner;

import com.eticaplanner.eticaPlanner.PlannerPage.TourApiDBService;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeListResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private NoticeService noticeService;

    public MainController(NoticeService noticeService){
        this.noticeService = noticeService;
    }

    @GetMapping({"","/"})
    public String index(Model model){
        System.out.println("[MainController] index()");
        List<NoticeListResponseDto> notices = this.noticeService.getAllMemos(); // 메모 목록 가져오기
        model.addAttribute("notices", notices);
        return "index";
    }

}
