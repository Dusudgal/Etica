package com.eticaplanner.eticaPlanner.review;

import com.eticaplanner.eticaPlanner.SessionDto;
import com.eticaplanner.eticaPlanner.review.dto.ReviewDto;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/Review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 리뷰 메인 이동
    @GetMapping("/ReviewIndex")
    public String ReviewIndex(Model model){
        System.out.println("[ReviewController] ReviewIndex()");
        return "Review/ReviewIndex";
    }

    // 리뷰 생성 이동
    @GetMapping("/ReviewGeneration")
    public String ReviewGeneration(Model model, HttpSession session) {
        SessionDto userSession = (SessionDto) session.getAttribute("sessionInfo"); // 수정: sessionInfo로 변경
        if (userSession == null || (userSession.getUser_id() == null && userSession.getKakao_id() == null)) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }
        System.out.println("[ReviewController] ReviewGeneration()");
        return "Review/ReviewGeneration";
    }


    // 리뷰 더보기 이동
    @GetMapping("/ReviewPlus")
    public String ReviewPlus(Model model) {
        System.out.println("[ReviewController] ReviewPlus()");
        return "Review/ReviewPlus";
    }

    // 내 리뷰 페이지 이동시 로그인 되어 있는지 확인
    @GetMapping("/ReviewMy")
    public String ReviewMy(Model model, HttpSession session) {
        System.out.println("[ReviewController] ReviewMy()");
        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");

        // userSession이 null인 경우
        if (userSession == null || (userSession.getUser_id() == null && userSession.getKakao_id() == null)) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }

        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();
        // 사용자 리뷰 조회
        List<ReviewDto> userReviews = reviewService.SearchReview(userId);
        System.out.println("리뷰 개수: " + userReviews.size()); // 리뷰 개수 확인

        // 모델에 리뷰 리스트 추가
        model.addAttribute("userReviews", userReviews);

        return "Review/ReviewMy"; // 내 리뷰 페이지로 이동
    }

    // 리뷰 수정 이동
    @PostMapping("/ReviewEdit")
    public String ReviewEdit( HttpSession session, Model model , @ModelAttribute ReviewDto userreviewdto) {
        System.out.println("[ReviewController] ReviewEdit()");

        // 세션에서 사용자 정보 가져오기
        SessionDto userSession = (SessionDto) session.getAttribute("sessionInfo");

        if (userSession == null || (userSession.getUser_id() == null && userSession.getKakao_id() == null)) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }

        String userId = userSession.getUser_id() != null ? userSession.getUser_id() : userSession.getKakao_id();
        // 리뷰 ID로 리뷰 DTO를 가져오는 서비스 호출
        ReviewDto reviewDto = reviewService.getReview(userId , userreviewdto.getReviewId()); // 서비스 메서드 추가 필요
        if (reviewDto == null) {
            // 리뷰가 존재하지 않을 경우 처리
            System.out.println("리뷰가 존재하지 않습니다.");
            return "redirect:/Review/ReviewMy"; // 내 리뷰 페이지로 리다이렉트
        }
        model.addAttribute("review", reviewDto); // 모델에 리뷰 추가
        return "Review/ReviewEdit"; // JSP 페이지로 이동
    }


    // 생성
    @PostMapping("/create")
    public String reviewSubmit(ReviewDto reviewDto, HttpSession session){
        System.out.println("[ReviewController] ReviewSubmit()");
        System.out.println(" reviewTitle " + reviewDto.getReviewTitle());
        System.out.println(" reviewContent " + reviewDto.getReviewContent());

        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");

        if (userSession.getUser_id() == null && userSession.getKakao_id() == null) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }
        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();

        boolean result = reviewService.saveReview(reviewDto, userId);
        System.out.println( result ? "성공" : "실패");


        return "redirect:/Review/ReviewMy";
    }
    
    // 세션에 있는 userId로 리뷰 조회
    @PostMapping("/update")
    public String editReview(ReviewDto reviewDto, HttpSession session) {
        System.out.println("[ReviewController] ReviewUpdate()");
        System.out.println(" reviewTitle: " + reviewDto.getReviewTitle());
        System.out.println(" reviewContent: " + reviewDto.getReviewContent());

        // 세션에서 사용자 정보 불러오기
        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");

        if (userSession.getUser_id() == null && userSession.getKakao_id() == null) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }
        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();
        //

        boolean result = reviewService.editReview(reviewDto, userId);
        System.out.println(result ? "수정 성공" : "수정 실패");

        return "redirect:/Review/ReviewMy";
    }
    
    // 삭제
    @PostMapping("/delete")
    public String deleteReview(@RequestParam Integer reviewId, HttpSession session) {
        System.out.println("[ReviewController] deleteReview()");

        SessionDto userSession = (SessionDto)session.getAttribute("sessionInfo");

        if (userSession.getUser_id() == null && userSession.getKakao_id() == null) {
            return "redirect:/user/sign-in-view"; // 로그인 페이지로 리다이렉트
        }
        String userId = userSession.getKakao_id() != null ? userSession.getKakao_id() : userSession.getUser_id();


        boolean result = reviewService.deleteReview(reviewId);
        System.out.println(result ? "삭제 성공" : "삭제 실패");

        return "redirect:/Review/ReviewMy"; // 내 리뷰 페이지로 리다이렉트
    }


}
