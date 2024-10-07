<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/login_ngStyle.css'/>" type="text/css">
    <title>관리자 로그인 페이지</title>
</head>
<body>
    <div class="container">
        <div class="login-wrapper">
            <h2>ETICA ADMIN</h2>
            <form method="post" action="<c:url value='/Admin/loginconfirm'/>" id="admin-login">
                <input type="text" name="admin_id" placeholder="아이디를 입력하세요.">
                <input type="password" name="admin_pw" placeholder="비밀번호를 입력하세요.">
            <input type="submit" value="로그인">
            <h3> 로그인이 실패하였습니다 </h3>
            </form>
        </div>
    </div>
</body>
</html>