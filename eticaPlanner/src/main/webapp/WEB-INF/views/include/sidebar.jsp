<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="sidebar" id="sidebar">
<c:set var="sessionInfo" value="${sessionScope.sessionInfo}" />
    <button id="closeSidebar"></button>
    <%-- 로그인 시 --%>
    <c:if test="${not empty sessionInfo.user_id}">
        <a href="/user/sign-out" class="mr-5">로그아웃</a>
        <a href="/MyPage/mypage?userId=${sessionInfo.user_id}" class="mr-5" >My Page</a>
    </c:if>
    <%-- 카카오 로그인 시 --%>
    <c:if test="${not empty sessionInfo.kakao_id}">
        <a href="/user/sign-out" class="mr-5">로그아웃</a>
        <a href="/MyPage/mypage?userId=${sessionInfo.kakao_id}" class="mr-5" >My Page</a>
    </c:if>
    <%-- 비로그인 시 --%>
    <c:if test="${ empty sessionInfo.user_id && empty sessionInfo.kakao_id }">
        <a href="/user/sign-in-view" >로그인</a>
    </c:if>
    <a href="/board">공지사항</a>
    <a href="/Review/ReviewIndex">Review</a>
    <a href="/Planner/PlannerPage">Planner</a>
    <a href="Admin/signin">관리자 버튼</a>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const sidebar = document.getElementById('sidebar');
        const toggleButton = document.getElementById('toggleSidebar');
        const closeButton = document.getElementById('closeSidebar');

        toggleButton.addEventListener('click', function() {
            sidebar.classList.add('show'); // 사이드바를 보여줌
            toggleButton.style.display = 'none'; // 열기 버튼 숨김
        });

        closeButton.addEventListener('click', function() {
            sidebar.classList.remove('show'); // 사이드바 숨김
            toggleButton.style.display = 'block'; // 열기 버튼 다시 표시
        });
    });
</script>
