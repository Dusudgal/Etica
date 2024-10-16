<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/modify_ng.css'/>" type="text/css">
    <title>계정 잠금 표시</title>
</head>
<body onload="startCountdown(${lockTime})">
    <div class="container">
        <h2>잘못된 로그인 시도로 인해 계정 잠금 상태입니다.</h2>
        <h3 id="remaining-time"></h3>
        <div class="menu">
            <ul>
                <li id="retry-link" style="display:none;"><a href="<c:url value='/Admin/signin'/>">다시 시도 하기</a></li>
            </ul>
        </div>
    </div>
</body>
<jsp:include page="lockPage_js.jsp"/>
</html>