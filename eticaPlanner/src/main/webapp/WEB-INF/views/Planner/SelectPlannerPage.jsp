<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Etica Travel Planner</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/PlannerPage.css' />">
</head>
<body>
    <div id="title">
        <label>제목 : <input type="text" id="TourTitle"> </label>
        <label>여행 첫째 날짜: <input type="date" id="startDate"> </label>
        <label>여행 마지막 날짜: <input type="date" id="endDate"> </label>
        <button class="saveData">저장</button>
    </div>
    <div class="container">
        <div class="row">
            <div id="dayButtons"></div>
        </div>
        <div class="row">
            <div>일정 일수: <span id="duration">0</span>일</div>
        </div>
        <div class="row large" id="map" >지도</div>
        </div>
    </div>
    <jsp:include page="SelectPlannerPage_js.jsp"/>
</body>
</html>