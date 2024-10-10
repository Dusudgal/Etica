<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="travel-planner" class="information-section">
    <div class="middle-container">
        <h2>여행 플래너 목록</h2>
        <div class="planner-list">
            <c:forEach var="planner" items="${plannerList}">
                <form action="${pageContext.request.contextPath}/Planner/ViewPlan" method="POST">
                    <label>
                        <p>${planner.tour_title}</p>
                        <input type="hidden" name="tour_title" value="${planner.tour_title}">
                    </label>
                    <button type="submit">플래너 조회</button>
                </form>
            </c:forEach>
        </div>
    </div>
</div>