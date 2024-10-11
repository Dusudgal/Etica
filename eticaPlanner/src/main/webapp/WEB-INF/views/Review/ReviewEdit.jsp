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
    <div class="revieweditmy_bodies">
        <h1>Review Edit</h1>
        <a href="/Review/ReviewIndex" class="button">Review Home</a>
        <a href="/" class="button">Home</a>
    </div>
    <div class="review_box3">
        <c:if test="${not empty review}">
            <form action="/Review/update" method="post">
                <input type="hidden" name="reviewId" value="${review.reviewId}"/>
                <h2>${review.tourTitle}</h2>
                <label for="reviewTitle">제목</label>
                <input type="text" name="reviewTitle" value="${review.reviewTitle}" required/><br/>
                <label for="reviewContent">내용</label>
                <textarea name="reviewContent" rows="4" required>${review.reviewContent}</textarea><br/> <!-- textarea로 변경 -->
                <button type="submit" class="editbutton">수정 완료</button>
                <a href="/Review/ReviewMy" class="editbutton">취소</a>
            </form>
        </c:if>
        <c:if test="${empty review}">
            <p>리뷰를 불러오는 데 실패했습니다.</p>
        </c:if>
    </div>
</body>
</html>
