<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/MainIndex.css' />">
    <title>Etica Planner</title>
</head>
<body>
    <div class="container">
        <h1> hello main index </h1>
        <div class="image-text-container">
            <img src="<c:url value='/Resources/Main_ban.jpg' />" alt="Etica Planner Logo">
            <p>Etica Planner를 찾아주셔서 감사합니다.</p>
        </div>
    </div>
     <div class="button-container">
         <div class="button-container">
            <span class="mr-5">${user_nickname}님 안녕하세요!</span> </br>

                <div class="PlanSelect"> </div>
            <a href="/Planner/PlannerPage" class="button">Planner 생성 버튼</a>
         </div>
     </div>

     <jsp:include page="PlannerIndex_js.jsp"/>
</body>
</html>