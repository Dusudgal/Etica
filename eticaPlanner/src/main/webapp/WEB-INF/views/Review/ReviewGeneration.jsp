<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/ReviewIndex.css'/>">
    <link rel="stylesheet" href="<c:url value='/Resources/css/signInStyle.css' />" type="text/css">
    <title>Review Main</title>
</head>
<body>
    <div class="bodies">
        <h1>Review</h1>
        <div class="button-container">
            <a href="/Review/ReviewMy" class="button">My Review</a> <!-- 나의 리뷰 -->
            <a href="/" class="button">Home</a> <!-- 홈으로 돌아가기 -->
        </div>
    <form action="/Review/create"  method="post" enctype="multipart/form-data">
            <div class="review_box">
                <h2>관광지 이름</h2>
                <div class="form-group">
                    <label for="reviewTitle">리뷰 제목</label>
                    <input type="text" id="reviewTitle" name="reviewTitle" required>
                </div>
                <div class="form-group">
                    <label for="reviewContent">리뷰 내용</label>
                    <textarea id="reviewContent" name="reviewContent" rows="4" required></textarea>
                </div>
                <div class="form-group">
                    <label for="fileUpload">첨부 파일</label>
                    <input type="file" id="fileUpload" name="reImage" >
                </div>
                <div class="form-group">
                    <button type="submit">작성</button>
                </div>
            </div>
    </form>
</body>
</html>
