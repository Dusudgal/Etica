<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/ReviewIndex.css' />">
    <link rel="stylesheet" href="<c:url value='/Resources/css/signInStyle.css' />" type="text/css">
    <title>Review Plus</title>
</head>
<body>
    <div class="bodies">
        <div class="button-container">
        <a href="/Review/ReviewIndex" class="button">Review Home</a> <!-- 리뷰 홈으로 돌아가기 -->
        <a href="/" class="button">Home</a> <!-- 홈으로 돌아가기 -->
        </div>
    </div>
    <h1>${tourTitle}</h1>
    <div class="reviews">
            <c:forEach var="review" items="${reviews}">
                <div class="review_box2"> <!-- 각 리뷰를 감싸는 div 추가 -->
                    <h3 class="title">${review.reviewTitle}</h3>
                    <p class="review">${review.reviewContent}</p>
                </div>
            </c:forEach>
        </div>
</body>
</html>
