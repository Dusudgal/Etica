<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/modify_ok.css'/>" type="text/css">
    <title>관리자 정보 수정 성공</title>
</head>
<body>
    <div class=head>
    <div class="container">
    <h2>관리자 정보가 성공적으로 수정되었습니다.</h2>
        <div class="menu">
            <ul>
                <li><a href="<c:url value='/'/>">홈페이지로 이동</a></li>
                <li><a href="<c:url value='/Admin/signin'/>">다시 로그인 하기</a></li>
            </ul>
        </div>
    </div>
    </div>
</body>
</html>