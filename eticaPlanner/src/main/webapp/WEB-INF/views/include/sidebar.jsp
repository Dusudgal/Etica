<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="sidebar" id="sidebar">
    <button id="closeSidebar">닫기</button>
    <a href="/Review/ReviewIndex">Review 버튼</a>
    <a href="/Planner/PlannerIndex">Planner 버튼</a>
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
