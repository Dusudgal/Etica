package com.eticaplanner.eticaPlanner.Admin;

import com.eticaplanner.eticaPlanner.Admin.entity.AdminDTo;
import io.micrometer.common.util.internal.logging.InternalLogLevel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.text.AttributedString;


@Controller
@RequestMapping("/Admin")
public class AdminController {

    AdminDTo admin;
    private String nextPage;

    @Autowired
    public AdminService adminService;

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
                                 Model model){
        System.out.println("[AdminController] loginConfirm()");

        try {
            AdminDTo admin = adminService.loginConfirm(admin_id,admin_pw);
            session.setAttribute("loginedAdminVo",admin);
            session.setMaxInactiveInterval(60*30);
            model.addAttribute("viewName","Admin/login_ok");
            this.nextPage ="template/Adminlayout";
            System.out.println("[AdminController] login success!");
            return this.nextPage;
        } catch (IllegalArgumentException e) {
            System.out.println("[AdminController] login Fail!");
            return "Admin/login_ng";
        } catch (AccountLockedException e) {
            System.out.println("[AdminController] account locked!");
            model.addAttribute("lockTime",adminService.getRemainingLockTime(admin_id));
            return "Admin/lockPage";
        }
    }


    //관리자 정보 수정
    @GetMapping("/modifyprofile")
    public String adminmodifyprofile(Model model,HttpSession session){
        System.out.println("[AdminController] AdminmodifyProfile()");

        AdminDTo admin = (AdminDTo) session.getAttribute("loginedAdminVo");
        model.addAttribute("admin", admin);
        model.addAttribute("viewName","Admin/modifyprofile");
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

            model.addAttribute("viewName","Admin/modify_ok");
            return "template/Adminlayout";
        } catch (Exception e){
            model.addAttribute("errorMsg",e.getMessage());
            model.addAttribute("viewName","Admin/modify_ng");
            return "template/Adminlayout";
        }
    }

    //여행지 추가
    @GetMapping("/add_travel")
    public String adminaddtravel(Model model){
        System.out.println("[AdminController] Adminadd_travel()");
        model.addAttribute("viewName","Admin/add_travel");
        this.nextPage = "template/Adminlayout";
        return this.nextPage;
    }

    //여행지 정보 수정
    @GetMapping("/modify_travel")
    public String adminmodifytravel(Model model){
        System.out.println("[AdminController] Adminmodify_travel()");
        model.addAttribute("viewName","Admin/modify_travel");
        this.nextPage = "template/Adminlayout";
        return this.nextPage;
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){
        System.out.println("[AdminController] Admin logout()");
        session.invalidate(); //세션무효화
        return "redirect:/Admin/signin"; //로그인 페이지로 리디렉션
    }


}
