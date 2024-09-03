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
        <h3>Etica Admin Page</h3>
        <div class="menu">
            <ul>
                <li><a href="#main">홈페이지</a></li>
                <li><a href="#add">여행지 추가</a></li>
                <li><a href="#edit">여행지 수정</a></li>
                <li><a href="<c:url value='/Admin/modifyprofile'/>">내 정보 수정</a></li>
                <li><a href="#logout">로그아웃</a></li>
            </ul>
        </div>
    </div>
</body>
</html>