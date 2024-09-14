<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 페이지</title>
</head>
<body>
<div class="text-center">
    <a href="${kakaoLoginUrl}">
        <img src="<c:url value='/Resources/kakao_login_medium_wide.png' />" alt="카카오 로그인">
    </a>
</div>

<!-- 에러 메시지가 있을 때 표시 -->
<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>
</body>
</html>