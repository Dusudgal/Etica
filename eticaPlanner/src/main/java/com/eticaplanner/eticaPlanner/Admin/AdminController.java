package com.eticaplanner.eticaPlanner.Admin;

import com.eticaplanner.eticaPlanner.Admin.entity.AdminDTo;
import com.eticaplanner.eticaPlanner.Admin.entity.TravelDTO;
import com.eticaplanner.eticaPlanner.Admin.entity.TravelEntity;
import com.eticaplanner.eticaPlanner.PlannerPage.controller.ApiComponent;
import com.eticaplanner.eticaPlanner.noticeBoard.dto.NoticeListResponseDto;
import com.eticaplanner.eticaPlanner.noticeBoard.service.BoardService;
import com.eticaplanner.eticaPlanner.noticeBoard.service.NoticeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/Admin")
public class AdminController {

    AdminDTo admin;
    private String nextPage;

    @Autowired
    private ApiComponent apiKeys;

    @Autowired
    public AdminService adminService;

    @Autowired
    public NoticeService noticeService;

    //로그인
    @GetMapping("/signin")
    public String adminsignin() {
        System.out.println("[AdminController] AdminSigninController()");
        return "Admin/signin";
    }

    //로그인 성공,실패
    @PostMapping("/loginconfirm")
    public String adminloginform(@RequestParam String admin_id,
                                 @RequestParam String admin_pw,
                                 HttpSession session,
                                 Model model) {
        System.out.println("[AdminController] loginConfirm()");

        try {
            AdminDTo admin = adminService.loginConfirm(admin_id, admin_pw);
            session.setAttribute("loginedAdminVo", admin);
            session.setMaxInactiveInterval(60 * 30);
            model.addAttribute("viewName","Admin/login_ok");
            this.nextPage = "template/Adminlayout";
            System.out.println("[AdminController] login success!");
            return this.nextPage;
        } catch (IllegalArgumentException e) {
            System.out.println("[AdminController] login Fail!");
            int failedAttempts = adminService.getFailedAttempts(admin_id);
            model.addAttribute("failedAttempts",failedAttempts);
            return "Admin/login_ng";
        } catch (AccountLockedException e) {
            System.out.println("[AdminController] account locked!");
            model.addAttribute("lockTime", adminService.getRemainingLockTime(admin_id));
            return "Admin/lockPage";
        }
    }


    //관리자 정보 수정
    @GetMapping("/modifyprofile")
    public String adminmodifyprofile(Model model, HttpSession session) {
        System.out.println("[AdminController] AdminmodifyProfile()");

        AdminDTo admin = (AdminDTo) session.getAttribute("loginedAdminVo");
        model.addAttribute("admin", admin);
        model.addAttribute("viewName", "Admin/modifyprofile");
        this.nextPage = "template/Adminlayout";
        return this.nextPage;
    }

    //관리자 정보 수정 폼 제출 처리를 위한 메서드
    @PostMapping("/modifyprofile")
    public String handleAdminModifyProfile(
            @RequestParam("admin_id") String adminId,
            @RequestParam("admin_password") String adminPassword,
            @RequestParam("admin_password_again") String adminPasswordAgain,
            Model model, HttpSession session) {

        try {
            AdminDTo admin = (AdminDTo) session.getAttribute("loginedAdminVo");
            admin.setAdminId(adminId);

            // 새 비밀번호와 새 비밀번호 확인이 서로 일치하는치 확인
            if (!adminPassword.equals(adminPasswordAgain)) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            // 새 비밀번호가 있는 경우에만 업데이트
            if (adminPassword != null && !adminPassword.isEmpty()) {
                admin.setAdminPw(adminPassword);
            }
            // 서비스 호출하여 관리자 정보 업데이트
            adminService.updateAdminInfo(admin);
            session.invalidate();

            model.addAttribute("viewName", "Admin/modify_ok");
            return "template/Adminlayout";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("viewName", "Admin/modify_ng");
            return "template/Adminlayout";
        }
    }

    //여행지 추가
    @GetMapping("/add_travel")
    public ModelAndView adminaddtravel() {
        System.out.println("[AdminController] Adminadd_travel()");
        ModelAndView mav = new ModelAndView("template/Adminlayout");
        String map_key = apiKeys.map_apikey(); // API키를 가져옴
        mav.addObject("map_key", map_key); // API 키를 뷰에 전달
        mav.addObject("viewName", "Admin/add_travel");
        return mav;
    }
    @PostMapping("/add_travel")
    public ModelAndView addTravel(@ModelAttribute TravelDTO travelDTO) {
        System.out.println("[AdminController] addTravel()");

        Boolean result = adminService.addTravel(travelDTO);
        if(result){
            ModelAndView mav = new ModelAndView("redirect:/Admin/list_travel");
            return mav;
        }
        ModelAndView mav = new ModelAndView("redirect:/Admin/fail");
        return mav;
    }

    // 여행지 리스트 정렬
    @GetMapping("/list_travel")
    public ModelAndView getTravelList(Model model){
        System.out.println("[AdminController] getTravelList()");
        ModelAndView mav = new ModelAndView("template/Adminlayout");
        List<TravelEntity> travels = adminService.getAllTravels();
        model.addAttribute("travels", travels);
        mav.addObject("travels",travels);
        mav.addObject("viewName","Admin/list_travel");

        return mav;
    }

    // 관리자가 추가한 여행지 삭제
    @PostMapping("/delete_travel")
    public String deleteTravel(@RequestParam("id") String travelName, RedirectAttributes redirectAttributes) {
        System.out.println("[AdminController] delete_travel()");
        try {
            adminService.deleteTravelByName(travelName);
            redirectAttributes.addFlashAttribute("sucessMessage", "여행지가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "여행지 삭제중 오류가 발생하였습니다.");
        }
        return "redirect:/Admin/list_travel";
    }

    // 관리자가 추가한 여행지 수정
    @GetMapping("/edit_travel")
    public ModelAndView showEditTravelPage(@RequestParam("id") Long travelId, Model model) {
        System.out.println("[AdminController] showEditTravelPage()");
        TravelEntity travel = adminService.getTravelById(travelId);

        // 카카오 API 키를 전달하여 뷰에 제공
        String mapKey = apiKeys.map_apikey();

        // ModelAndView 생성
        ModelAndView mav = new ModelAndView("template/Adminlayout");
        mav.addObject("map_key", mapKey);
        mav.addObject("travel", travel);
        mav.addObject("viewName", "Admin/edit_travel"); // 실제 컨텐츠 뷰

        return mav;
    }

    // 관리자가 추가한 여행지 수정2
    @PostMapping("/update_travel")
    public String updateTravel(@ModelAttribute TravelDTO travelDTO, RedirectAttributes redirectAttributes) {
        try {
            adminService.updateTravel(travelDTO);
            redirectAttributes.addFlashAttribute("successMessage", "여행지 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "여행지 정보 업데이트 중 문제가 발생했습니다.");
        }
        // 변경 후 목록 페이지로 리디렉션
        return "redirect:/Admin/list_travel";
    }

    // 관리자 공지사항 목록 이동
    @GetMapping("/admin_board")
    public ModelAndView adminboard(Model model){
        System.out.println("[AdminController] adminboard()");
        ModelAndView mav = new ModelAndView("template/Adminlayout");
        List<NoticeListResponseDto> notices = noticeService.getAllMemos();
        model.addAttribute("notices", notices);
        mav.addObject("viewName","Admin/admin_board");

        return mav;
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){
        System.out.println("[AdminController] Admin logout()");
        session.invalidate(); //세션무효화
        return "redirect:/Admin/signin"; //로그인 페이지로 리디렉션
    }


}
