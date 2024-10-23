<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="look-information-section" class="information-section-unique">
    <div class="middle-container">
        <table class="middle-table">
        <c:set var="sessionInfo" value="${sessionScope.sessionInfo}" />
        <c:if test="${not empty sessionInfo.user_id}">
            <tr>
                <th>아이디</th>
                <td>${user.userId}</td>
            </tr>
            <tr>
                <th>닉네임</th>
                <td>${user.userNickname}</td>
                </tr>
            <tr>
                <th>성함</th>
                <td>${user.userName}</td>
            </tr>
            <tr>
                <th>생년월일</th>
                <td>${user.userBirth}</td>
            </tr>
        </c:if>
        <%-- 카카오 로그인 시 --%>
        <c:if test="${not empty sessionInfo.kakao_id}">
            <tr>
                <th>아이디</th>
                <td>카카오 로그인 사용자 입니다</th>
            </tr>
        </c:if>
        </table>
    </div>
</div>