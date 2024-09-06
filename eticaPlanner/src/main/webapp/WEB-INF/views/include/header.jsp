<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="h-100 d-flex justify-content-between align-items-center">

    <%-- logo --%>
    <div>
        <h1 class="ml-5" style="padding-left: 30px;"><a href="/" style="color : black">ETICA</a></h1>
    </div>

    <%-- 로그인 정보 --%>
    <div>
        <%-- 로그인 시 --%>
            <c:if test="${not empty user_no}">
                <span>${user_name}님 안녕하세요</span>
                <a href="/user/sign-out" class="mr-5">로그아웃</a>
            </c:if>
        <%-- 비로그인 시 --%>
            <c:if test="${empty user_no}">
                <a href="/user/sign-in-view" class="mr-5">로그인</a>
            </c:if>
    </div>
</div>