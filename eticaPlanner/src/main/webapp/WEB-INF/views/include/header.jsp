<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<header class="d-flex justify-content-between align-items-start p-3">
    <%-- logo --%>
    <div class="d-flex align-items-flex-start">
        <a href="/">
            <img src="<c:url value='/Resources/Img/Eti.png' />" >
        </a>
    </div>

    <%-- 로그인 정보 --%>

    <div class="login-info align-items-center d-flex">
        <c:set var="sessionInfo" value="${sessionScope.sessionInfo}" />
        <%-- 로그인 시 --%>
            <c:if test="${not empty sessionInfo.user_id}">
                <a href="/MyPage/mypage?userId=${sessionInfo.user_id}" class="mr-5" >My Page</a>
                <span class="mr-5">${sessionInfo.user_nickname}님 안녕하세요!</span>
                <a href="/user/sign-out" class="mr-5">로그아웃</a>
            </c:if>
        <%-- 카카오 로그인 시 --%>
            <c:if test="${not empty sessionInfo.kakao_id}">
                <a href="/MyPage/mypage?userId=${sessionInfo.kakao_id}" class="mr-5" >My Page</a>
                <span class="mr-5">${sessionInfo.kakao_nickname}님 안녕하세요!</span>
                <a href="/user/sign-out" class="mr-5">로그아웃</a>
            </c:if>
        <%-- 비로그인 시 --%>
            <c:if test="${ empty sessionInfo.user_id && empty sessionInfo.kakao_id }">
                <a href="/user/sign-in-view" class="ml-3">로그인</a>
            </c:if>
    </div>
</header>
