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
        <%-- 세션 정보 설정 --%>
        <c:set var="sessionInfo" value="${sessionScope.sessionInfo}" />

        <%-- 로그인 시 --%>
            <c:if test="${not empty sessionInfo.user_id}">
                <span class="mr-5">${sessionInfo.user_nickname}님 안녕하세요!</span>
                <a href="/user/sign-out" class="mr-5">로그아웃</a>
            </c:if>
        <%-- 카카오 로그인 시 --%>
            <c:if test="${not empty sessionInfo.kakao_id}">
                <span class="mr-5">${sessionInfo.kakao_nickname}님 안녕하세요!</span>
                <a href="/user/sign-out" class="mr-5">로그아웃</a>
            </c:if>
        <%-- 비로그인 시 --%>
            <c:if test="${empty sessionInfo.user_id and empty sessionInfo.kakao_id}">
                <a href="/user/sign-in-view" class="mr-5">로그인</a>
            </c:if>
    </div>
</div>