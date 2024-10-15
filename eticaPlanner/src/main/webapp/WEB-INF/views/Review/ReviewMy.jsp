<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/Resources/ReviewIndex.css">
    <link rel="stylesheet" href="/Resources/css/signInStyle.css" type="text/css">
    <title>My Review</title>
</head>
<body>
    <div class="revieweditmy_bodies">
        <h1>My Review</h1>
        <a href="/Review/ReviewIndex" class="button">Review Home</a>
        <a href="/" class="button">Home</a>
    </div>

    <div class="reviews">
        <c:set var="sessionInfo" value="${sessionScope.sessionInfo}" />
        <c:if test="${not empty userReviews}">
            <c:forEach var="review" items="${userReviews}">
                <div class="review_box2">
                        <form action="/Review/ReviewEdit" method="post" style="display:inline;">

                            <input type="hidden" value="${review.reviewId}" name="reviewId"/>
                            <h2 >${review.tourTitle}</h2>
                            <h3 class="review-title">${review.reviewTitle}</h3>
                            <p class="review-contents">${review.reviewContent}</p>
                            <c:if test="${not empty sessionInfo.user_id }"> <!-- 로그인한 경우만 표시 -->
                                <button type="submit" class="button">수정</button>
                            </c:if>
                        </form>
                        <c:if test="${not empty sessionInfo.user_id}"> <!-- 로그인한 경우만 표시 -->
                            <form action="/Review/delete" method="post" style="display:inline;">
                                <input type="hidden" name="reviewId" value="${review.reviewId}" /> <!-- 리뷰 ID를 숨긴 필드로 추가 -->
                                <button type="submit" class="button" onclick="return confirm('정말 삭제하시겠습니까?');" >삭제</button>
                            </form>
                        </c:if>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty userReviews}">
            <p>작성한 리뷰가 없습니다.</p>
        </c:if>
    </div>
<div class="pagination">
    <c:forEach var="i" begin="1" end="${totalPages}">
        <c:choose>
            <c:when test="${i == currentPage}">
                <span class="current-page">${i}</span>
            </c:when>
            <c:otherwise>
                <a href="?currentPage=${i}" class="button">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="?currentPage=${totalPages}" class="button">마지막</a>
    </c:if>
</div>


</body>
</html>
