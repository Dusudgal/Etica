package com.eticaplanner.eticaPlanner.review;

import com.eticaplanner.eticaPlanner.review.dto.ReviewDto;
import com.eticaplanner.eticaPlanner.review.entity.ReviewEntity;
import com.eticaplanner.eticaPlanner.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    private final String uploadImg = "upload/"; // 파일 저장 경로 (상대 경로로 수정)

    // 생성 후 저장
    public boolean saveReview(ReviewDto reviewDto, String userId) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setTourTitle(reviewDto.getTourTitle()); // 관광지 이름
        reviewEntity.setReTitle(reviewDto.getReviewTitle()); // 리뷰 제목
        reviewEntity.setReText(reviewDto.getReviewContent()); // 리뷰 내용
        reviewEntity.setUserId(userId); // 사용자 ID
        reviewEntity.setReDate(LocalDateTime.now()); // 현재 시간으로 설정

        // DB에 저장
        ReviewEntity result = reviewRepository.save(reviewEntity);
        return result.getReviewId() != null; // 저장 성공 여부
    }

    // 조회
    public List<ReviewDto> SearchReview(String userId) {
        System.out.println("[ReviewService] SearchReview()");

        // 사용자 리뷰 정보를 조회하는 로직
        List<ReviewEntity> userReviews = reviewRepository.findByUserId(userId); // List<ReviewEntity>

        // ReviewEntity 리스트를 ReviewDto 리스트로 변환
        List<ReviewDto> reviewDtoList = userReviews.stream()
                .map(this::convertToDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());

        if (!reviewDtoList.isEmpty()) {
            // 로그 출력 (저장된 리뷰 확인)
            for (ReviewDto reviewDto : reviewDtoList) {
                // 관광지 번호 출력 부분 제거
                System.out.println("관광지 이름: " + reviewDto.getTourTitle());
                System.out.println("리뷰 ID: " + reviewDto.getReviewId()); // 리뷰 ID 출력
                System.out.println("리뷰 제목: " + reviewDto.getReviewTitle());
                System.out.println("리뷰 내용: " + reviewDto.getReviewContent());
            }
        } else {
            System.out.println("해당 사용자의 리뷰가 없습니다.");
        }

        return reviewDtoList; // 변환된 DTO 리스트 반환
    }


    // 관광지별 리뷰 조회
    public List<ReviewDto> findReviewsByTourTitle(String tourTitle) {
        System.out.println("[ReviewService] searchReviewsByTourTitle()");

        // 관광지 이름으로 리뷰 정보를 조회하는 로직
        List<ReviewEntity> tourReviews = reviewRepository.findByTourTitle(tourTitle); // List<ReviewEntity>

        // ReviewEntity 리스트를 ReviewDto 리스트로 변환
        List<ReviewDto> reviewDtoList = tourReviews.stream()
                .map(this::convertToDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());

        if (!reviewDtoList.isEmpty()) {
            // 로그 출력 (저장된 리뷰 확인)
            for (ReviewDto reviewDto : reviewDtoList) {
                System.out.println("관광지 이름: " + reviewDto.getTourTitle());
                System.out.println("리뷰 ID: " + reviewDto.getReviewId()); // 리뷰 ID 출력
                System.out.println("리뷰 제목: " + reviewDto.getReviewTitle());
                System.out.println("리뷰 내용: " + reviewDto.getReviewContent());
            }
        } else {
            System.out.println("해당 관광지에 대한 리뷰가 없습니다.");
        }

        return reviewDtoList; // 변환된 DTO 리스트 반환
    }


    // ReviewEntity를 ReviewDto로 변환하는 메서드
    private ReviewDto convertToDto(ReviewEntity reviewEntity) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTourTitle(reviewEntity.getTourTitle());
        reviewDto.setReviewId(reviewEntity.getReviewId()); // 리뷰 ID 설정
        reviewDto.setReviewTitle(reviewEntity.getReTitle());
        reviewDto.setReviewContent(reviewEntity.getReText());
        return reviewDto;
    }


    //수정
    public boolean editReview(ReviewDto reviewDto, String userId) {
        System.out.println("[ReviewService] editReview()");

        // 사용자 ID로 특정 리뷰를 조회
        List<ReviewEntity> userReviews = reviewRepository.findByUserId(userId);

        // 수정할 리뷰를 찾기 (리뷰 ID로 필터링)
        ReviewEntity existingReview = userReviews.stream()
                .filter(review -> review.getReviewId().equals(reviewDto.getReviewId())) // 리뷰 ID로 필터링
                .findFirst()
                .orElse(null);

        if (existingReview == null) {
            System.out.println("해당 사용자의 리뷰가 존재하지 않습니다.");
            return false; // 사용자의 리뷰가 없음
        }

        // 리뷰 수정
        existingReview.setReTitle(reviewDto.getReviewTitle());
        existingReview.setReText(reviewDto.getReviewContent());

        // 수정된 리뷰를 DB에 저장
        reviewRepository.save(existingReview); // DB에 수정 내용 저장
        System.out.println("리뷰 수정 성공");
        return true; // 수정 성공
    }




    // 삭제
    public boolean deleteReview(Integer reviewId) {
        Optional<ReviewEntity> reviewEntityOptional = reviewRepository.findById(reviewId);

        if (reviewEntityOptional.isPresent()) {
            reviewRepository.delete(reviewEntityOptional.get()); // 리뷰 삭제
            return true; // 삭제 성공
        } else {
            System.out.println("리뷰가 존재하지 않습니다.");
            return false; // 리뷰가 없음
        }
    }

    public ReviewDto getReview(String userId, Integer reviewId) {
        System.out.println("[reviewService] getReview");
        ReviewEntity reviewreuslt = reviewRepository.findByReviewId(reviewId);
        if(reviewreuslt != null){
            ReviewDto userReview = convertToDto(reviewreuslt);
            return userReview;
        }
        return null;
    }
}
