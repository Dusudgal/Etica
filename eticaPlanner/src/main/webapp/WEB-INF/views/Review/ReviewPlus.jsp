<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
    <div class="button-container">
        <h1>Review Plus</h1>
        <a href="/Review/ReviewIndex" class="button">Review Home</a> <!-- 리뷰 홈으로 돌아가기 -->
        <a href="/" class="button">Home</a> <!-- 홈으로 돌아가기 -->
    </div>
    <div class="review_box">
        <div class="image"></div>
        <div class="title">리뷰 제목</div>
        <div class="review">리뷰 내용</div>
        <button class="more-button">더보기</button>
    </div>

</body>
</html>