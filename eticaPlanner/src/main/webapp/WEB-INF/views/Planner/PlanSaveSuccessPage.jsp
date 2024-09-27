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
            <h1> 플랜이 저장됐습니다. </h1>
         </div>
     </div>
</body>
</html>