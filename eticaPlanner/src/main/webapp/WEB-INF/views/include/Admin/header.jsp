<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="h-100 d-flex justify-content-between align-items-center">

    <%-- logo --%>
    <div>
        <h1 class="ml-5" style="padding-left: px; font-weight:bold;"><a href="/" style="color : black">ETICA ADMIN</a></h1>
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