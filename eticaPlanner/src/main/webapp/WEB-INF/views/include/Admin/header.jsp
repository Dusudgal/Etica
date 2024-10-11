<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="h-100 d-flex justify-content-between align-items-center">
<link rel="stylesheet" href="/Resources/css/signInStyle.css" type="text/css">

    <%-- logo --%>
    <div>
        <h1 class="ml-5" style="font-weight:bold;">
            <a href="/"><img src="<c:url value='/Resources/Img/eti.png'/>" alt="ETICA ADMIN" style="color : black; margin-top: 50px;"/></a>
        </h1>
    </div>

    <%-- 로그인 정보 --%>
    <div>
        <%-- 로그인 시 --%>
        <c:choose>
                <c:when test="${not empty sessionScope.loginedAdminVo}">
                    <span class="name-message">[관리자] ${sessionScope.loginedAdminVo.adminName} 님 안녕하세요</span>
                </c:when>
        <%-- 비로그인 시 --%>
            <c:otherwise>
                <span class="name=message">로그인이 필요합니다</span>
            </c:otherwise>
        </c:choose>
    </div>
</div>