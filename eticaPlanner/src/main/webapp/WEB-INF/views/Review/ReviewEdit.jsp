<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/ReviewIndex.css' />">
    <link rel="stylesheet" href="<c:url value='/Resources/css/signInStyle.css' />" type="text/css">
    <title>Review Edit</title>
</head>
<body>
    <div class="button-container">
        <h1>Review Edit</h1>
        <a href="/Review/ReviewIndex" class="button">Review Home</a>
        <a href="/" class="button">Home</a>
    </div>

    <div class="reviews">
        <c:if test="${not empty review}">
            <form action="/Review/update" method="post"> <!-- 실제 업데이트를 수행할 액션 -->
                <input type="hidden" name="reviewId" value="${review.reviewId}"/> <!-- 리뷰 ID를 숨은 필드로 추가 -->
                <label for="reviewTitle">제목:</label>
                <input type="text" name="reviewTitle" value="${review.reviewTitle}" required/><br/>

                <label for="reviewContent">내용:</label>
                <textarea name="reviewContent" required>${review.reviewContent}</textarea><br/>

                <button type="submit">수정 완료</button>
            </form>
            <a href="/Review/ReviewMy">취소</a>
        </c:if>
        <c:if test="${empty review}">
            <p>리뷰를 불러오는 데 실패했습니다.</p>
        </c:if>
    </div>
</body>
</html>
