<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>세로로 세 줄 나누기</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/PlannerPage.css' />">
</head>
<body>
    <div class="container">
        <div class="row">일정 일수</div>
        <div class="row">일정 메모</div>
        <div class="row large">지도</div>
        <div class="row">
            <ul class="touristSpotListUl"> <!-- 스크롤 적용되는 ul 태그 -->
                <li>관광지 리스트 검색
                    <div>
                        <input class="touristSpotSearch" type="text" />
                        <button class="touristSpotClick"> click </button>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <jsp:include page="PlannerPage_js.jsp"/>
</body>
</html>