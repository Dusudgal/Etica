<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="h-100 d-flex justify-content-between align-items-center">

    <%-- logo --%>
    <div>
        <h1><a href="/" class="logo">ETICA</a></h1>
    </div>

        <%-- 로그인 정보 --%>
        <div>
            <%-- 일반 로그인 시 --%>
            <c:if test="${not empty sessionScope.sessionInfo.user_id}">
                <span class="mr-5">${sessionScope.sessionInfo.user_nickname}님 안녕하세요!</span>
                <a href="/user/sign-out" class="mr-5">로그아웃</a>
            </c:if>

            <%-- 카카오 로그인 시 --%>
            <c:if test="${not empty sessionScope.sessionInfo.kakao_id}">
                <span class="mr-5">${sessionScope.sessionInfo.kakao_nickname}님 안녕하세요!</span>
                <a href="#" id="kakaoLogoutButton" class="mr-5">카카오 로그아웃</a>
            </c:if>

            <%-- 비로그인 시 --%>
            <c:if test="${empty sessionScope.sessionInfo.user_id and empty sessionScope.sessionInfo.kakao_id}">
                <a href="/user/sign-in-view" class="mr-5">로그인</a>
            </c:if>
        </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const kakaoLogoutButton = document.getElementById('kakaoLogoutButton');
        if (kakaoLogoutButton) {
            kakaoLogoutButton.addEventListener('click', function() {
                const url = 'https://kauth.kakao.com/oauth/logout';
                const clientId = '${kakaoApiKey}'; // 서버에서 가져온 client_id
                const clientSecret = '${kakaoClientSecret}'; // 서버에서 가져온 client_secret
                const logoutRedirectUri = '${logoutRedirectUri}'; // 서버에서 가져온 리다이렉트 URI
                location.href = url + '?client_id=' + clientId + '&logout_redirect_uri=' + logoutRedirectUri; // 문자열 결합
            });
        }
    });
</script>