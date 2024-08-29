<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인페이지</title>
    <link rel="stylesheet" href="<c:url value='/Resources/css/signInStyle.css' />" type="text/css">
</head>
<body>
<div class="login-wrapper">
    <h2>로그인</h2>
    <form method="post" action="#" id="login-form">
        <input type="text" name="userName" placeholder="아이디를 입력해주세요.">
        <input type="password" name="userPassword" placeholder="비밀번호를 입력해주세요.">
        <label for="remember-check">
            <input type="checkbox" id="remember-check">아이디 저장하기
            <a href="/User/sign-up-view">회원가입 하러가기</a>
        </label>
        <input type="submit" value="login">
    </form>
</div>
</body>
</html>