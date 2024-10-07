<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/login_okStyle.css'/>" type="text/css">
    <title>관리자페이지</title>
</head>
<body>
    <div class="head">
        <div class="menu">
            <ul>
                <li><a href="<c:url value='/'/>">홈페이지</a></li>
                <li><a href="<c:url value='/Admin/list_travel'/>">여행지 설정</a></li>
                <li><a href="<c:url value='/Admin/modifyprofile'/>">비밀번호 수정</a></li>
                <li><a href="<c:url value='/Admin/logout'/>">로그아웃</a></li>
            </ul>
        </div>
    </div>
</body>
</html>