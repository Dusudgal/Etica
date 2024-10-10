<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
        <div class="button-container">
            <a href="/Review/ReviewMy" class="button">My Review</a> <!-- 나의 리뷰 -->
            <a href="/" class="button">Home</a> <!-- 홈으로 돌아가기 -->
        </div>
    </div>
    <div class="search-box">
        <input type="text" class="touristSpotSearch" name="query" placeholder="관광지 검색" required /> <!-- 검색 입력 필드 -->
        <button type="button" class="touristSpotClick">검색</button> <!-- 검색 버튼 -->
        <a href="/Review/ReviewGeneration" class="more-button">Generation</a> <!-- 클릭 시 바로 생성 페이지로 이동 -->
    </div>

    <div class="search-results">
        <ul class="touristSpotListUl"></ul> <!-- 검색 결과를 표시할 리스트 -->
    </div>

    <!-- JavaScript 파일 포함 -->
    <script type="text/javascript" src="<c:url value='/Planner/PlannerPage_js.jsp'/>"></script>
</body>
</html>
