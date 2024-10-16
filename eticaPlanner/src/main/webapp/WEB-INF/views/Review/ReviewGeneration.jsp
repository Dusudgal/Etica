<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/ReviewIndex.css'/>">
    <link rel="stylesheet" href="<c:url value='/Resources/css/signInStyle.css' />" type="text/css">
    <title>Review Generation</title>
</head>
<body>
    <div>
        <div class="bodies">
            <a href="/Review/ReviewMy" class="button">My Review</a> <!-- 나의 리뷰 -->
            <a href="/" class="button">Home</a> <!-- 홈으로 돌아가기 -->
        </div>
        <form id="form" action="/Review/create" method="post" enctype="multipart/form-data" class="review_Generation">
            <div class="reviews">
                <div class="review_box">
                    <input type="hidden" id="tourTitle" name="tourTitle" value="${param.title}"> <!-- name을 'title'로 설정 -->
                    <h2>${param.title}</h2> <!-- 제목을 h2로 표시 -->
                    <div class="form-group">
                        <label for="reviewTitle">리뷰 제목</label>
                        <input type="text" id="reviewTitle" name="reviewTitle" required>
                    </div>
                    <div class="form-group">
                        <label for="reviewContent">리뷰 내용</label>
                        <textarea id="reviewContent" name="reviewContent" rows="4" required></textarea>
                    </div>
                    <div class="form-group">
                        <button type="submit" id="submit">작성</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <script>
        const submitBtn = document.getElementById("submit").addEventListener('click', submit);

        function submit() {
            console.log(document.getElementById("tourTitle").value);
        }
    </script>
</body>
</html>
