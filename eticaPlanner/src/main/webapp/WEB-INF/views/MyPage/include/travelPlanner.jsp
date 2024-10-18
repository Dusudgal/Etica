<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="travel-planner-section" class="information-section-unique">
    <div class="middle-container">
        <h2 class="planner-title">여행 플래너 목록</h2>
        <div class="planner-list">
            <c:forEach var="planner" items="${plannerList}">
                <form action="${pageContext.request.contextPath}/Planner/ViewPlan" method="POST" class="planner-form">
                    <label class="planner-label">
                        <p>${planner.tour_title}</p>
                        <input type="hidden" name="tour_title" value="${planner.tour_title}">
                    </label>
                    <div class="button-group">
                        <a href="#" class="planner-button">조회</a>
                        <a href="#" class="planner-button">수정</a>
                        <a href="#" class="planner-button">삭제</a>
                    </div>
                </form>
            </c:forEach>
        </div>
    </div>
</div>