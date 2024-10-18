package com.eticaplanner.eticaPlanner.review;

import com.eticaplanner.eticaPlanner.SessionDto;
import com.eticaplanner.eticaPlanner.review.dto.ReviewDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/Review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 리뷰 메인 이동
    @GetMapping("/ReviewIndex")
    public String ReviewIndex(Model model) {
        System.out.println("[ReviewController] ReviewIndex()");
        model.addAttribute("viewName", "Review/ReviewIndex");
        return "template/layout";
    }

    // 리뷰 생성 이동
    @GetMapping("/ReviewGeneration")
    public String ReviewGeneration(@RequestParam("title") String tourTitle, Model model, HttpSession session) {
        SessionDto userSession = (SessionDto) session.getAttribute("sessionInfo"); // 수정: sessionInfo로 변경
        if (userSession == null || (userSession.getUser_id() == null && userSession.getKakao_id() == null)) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }
        System.out.println("[ReviewController] ReviewGeneration()");
        model.addAttribute("viewName", "Review/ReviewGeneration");
        model.addAttribute("tourTitle", tourTitle);
        return "template/layout";
    }

    @GetMapping("/ReviewPlus")
    public String reviewPlus(@RequestParam("title") String title,
                             @RequestParam(defaultValue = "1") int currentPage,
                             Model model) {
        System.out.println("[ReviewController] ReviewPlus()");
        System.out.println("받은 관광지 이름: " + title); // 받은 title 출력

        // 페이지당 리뷰 수
        int pageSize = 5;

        // title을 기반으로 페이징된 리뷰 목록 조회
        Page<ReviewDto> reviews = reviewService.findReviewsByTourTitle(title, currentPage, pageSize); // 페이징된 리뷰 조회

        model.addAttribute("reviews", reviews.getContent()); // 리뷰 목록을 모델에 추가
        model.addAttribute("tourTitle", title); // 관광지 이름도 추가
        model.addAttribute("currentPage", currentPage); // 현재 페이지 추가
        model.addAttribute("totalPages", reviews.getTotalPages()); // 총 페이지 수 추가
        model.addAttribute("viewName", "Review/ReviewPlus"); // 뷰 이름 추가

        return "template/layout"; // 레이아웃 템플릿 반환
    }




    // 내 리뷰 페이지 이동시 로그인 되어 있는지 확인
    @GetMapping("/ReviewMy")
    public String ReviewMy(@RequestParam(defaultValue = "1") int currentPage, Model model, HttpSession session) {
        System.out.println("[ReviewController] ReviewMy()");
        SessionDto userSession = (SessionDto) session.getAttribute("sessionInfo");

        if (userSession == null || (userSession.getUser_id() == null && userSession.getKakao_id() == null)) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }

        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();

        // 페이지당 리뷰 수
        int pageSize = 5;
        // 사용자 리뷰 조회 (페이지네이션 포함)
        Page<ReviewDto> userReviews = reviewService.searchReview(userId, currentPage, pageSize);

        // 모델에 리뷰 리스트와 페이지 정보 추가
        model.addAttribute("userReviews", userReviews.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", userReviews.getTotalPages());

        model.addAttribute("viewName", "Review/ReviewMy");
        return "template/layout";
    }


    // 리뷰 수정 이동
    @PostMapping("/ReviewEdit")
    public String ReviewEdit(HttpSession session, Model model, @ModelAttribute ReviewDto userreviewdto) {
        System.out.println("[ReviewController] ReviewEdit()");

        // 세션에서 사용자 정보 가져오기
        SessionDto userSession = (SessionDto) session.getAttribute("sessionInfo");

        if (userSession == null || (userSession.getUser_id() == null && userSession.getKakao_id() == null)) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }

        String userId = userSession.getUser_id() != null ? userSession.getUser_id() : userSession.getKakao_id();
        // 리뷰 ID로 리뷰 DTO를 가져오는 서비스 호출
        ReviewDto reviewDto = reviewService.getReview(userId, userreviewdto.getReviewId()); // 서비스 메서드 추가 필요
        if (reviewDto == null) {
            // 리뷰가 존재하지 않을 경우 처리
            System.out.println("리뷰가 존재하지 않습니다.");
            return "redirect:/Review/ReviewMy"; // 내 리뷰 페이지로 리다이렉트
        }
        model.addAttribute("review", reviewDto); // 모델에 리뷰 추가
        model.addAttribute("viewName", "Review/ReviewEdit");
        return "template/layout";
    }


    // 생성
    @PostMapping("/create")
    public String reviewSubmit(ReviewDto reviewDto, HttpSession session, Model model) {

        System.out.println("[ReviewController] ReviewSubmit()");
        System.out.println(" tourTitle " + reviewDto.getTourTitle());
        System.out.println(" reviewTitle " + reviewDto.getReviewTitle());
        System.out.println(" reviewContent " + reviewDto.getReviewContent());

        SessionDto userSession = (SessionDto) session.getAttribute("sessionInfo");
        System.out.println(reviewDto.getTourTitle());
        if (userSession == null) {
            return "redirect:/user/sign-in-view";
        }

        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();

        boolean result = reviewService.saveReview(reviewDto, userId);
        System.out.println(result ? "성공" : "실패");
        model.addAttribute("tourTitle", reviewDto.getTourTitle());

        return "redirect:/Review/ReviewMy";
    }

    // 세션에 있는 userId로 리뷰 조회
    @PostMapping("/update")
    public String editReview(ReviewDto reviewDto, HttpSession session) {
        System.out.println("[ReviewController] ReviewUpdate()");
        System.out.println(" reviewTitle: " + reviewDto.getReviewTitle());
        System.out.println(" reviewContent: " + reviewDto.getReviewContent());

        // 세션에서 사용자 정보 불러오기
        SessionDto userSession = (SessionDto) session.getAttribute("sessionInfo");

        if (userSession.getUser_id() == null && userSession.getKakao_id() == null) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }
        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();
        //

        boolean result = reviewService.editReview(reviewDto, userId);
        System.out.println(result ? "수정 성공" : "수정 실패");


        return "redirect:/Review/ReviewMy"; // 내 리뷰 페이지로 리다이렉트
    }

    // 삭제
    @PostMapping("/delete")
    public String deleteReview(ReviewDto reviewDto, HttpSession session, Model model) {
        System.out.println("[ReviewController] deleteReview()");

        SessionDto userSession = (SessionDto) session.getAttribute("sessionInfo");

        if (userSession.getUser_id() == null && userSession.getKakao_id() == null) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }
        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();


        boolean result = reviewService.deleteReview(reviewDto.getReviewId());
        System.out.println(result ? "삭제 성공" : "삭제 실패");

        return "redirect:/Review/ReviewMy"; // 내 리뷰 페이지로 리다이렉트
    }


}
