<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="h-100 d-flex justify-content-between align-items-center">

    <%-- logo --%>
    <div>
        <h1 class="ml-5">ETICA</h1>
    </div>

    <%-- 로그인 정보 --%>
    <div>
        <%-- 로그인 시 --%>
            <span>김회원님 안녕하세요</span>
            <a href="/user/sign-out" class="mr-5">로그아웃</a>
        <%-- 비로그인 시 --%>
        <%-- 구현예정--%>
    </div>
</div>