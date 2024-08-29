<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="<c:url value='/Resources/css/signUpStyle.css' />" type="text/css">
</head>
<body>
<div class="container">
    <div class="user-container">
        <div class="header">
            <div>회원 정보를 입력해주세요!</div>
        </div>
        <div class="user-info">
            <div class="user-info-email">
                <div>* 아이디</div>
                <input type="text" />
            </div>
            <div class="user-info-pw">
                <div>* 비밀번호</div>
                <input type="password" />
            </div>
            <div class="user-info-pw-check">
                <div>* 비밀번호 확인</div>
                <input type="password" />
            </div>
            <div class="user-info-name">
                <div>* 이름</div>
                <input type="text" />
            </div>
            <div>
                <div>* 핸드폰 번호</div>
                <input type="text" />
            </div>
            <div>
                <div>* 생년월일(6글자)</div>
                <input type="text" />
            </div>
        </div>
        <div class="gender">
            <input type="radio" name="gender" />
            <label for="men">남성</label>
            <input type="radio" name="gender" />
            <label for="women">여성</label>
        </div>
        <div class="agree-check">
            <input type="checkbox" /> 이용약관 개인정보 수집 및 이용, 마케팅 활용
            선택에 모두 동의합니다.
        </div>
        <div class="btn">
            <button>가입하기</button>
        </div>
    </div>
</div>
</body>
</html>