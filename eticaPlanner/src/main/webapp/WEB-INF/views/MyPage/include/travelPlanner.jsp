<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="travel-planner-section" class="information-section-unique">
    <div class="middle-container">
        <h2 class="planner-title">여행 플래너 목록</h2>
        <div class="planner-list">
            <table>
                <c:forEach var="planner" items="${plannerList}">
                    <div class="planner-form">
                        <p>${planner.tour_title}</p>
                        <form action="${pageContext.request.contextPath}/Planner/ViewPlan" method="POST">
                            <input type="hidden" name="tour_title" value="${planner.tour_title}">
                            <button type="submit" class="planner-button">조회</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/Planner/ModifyPlan" method="POST">
                            <input type="hidden" name="tour_title" value="${planner.tour_title}">
                            <button type="submit" class="planner-button">수정</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/Planner/DeletePlan" method="POST">
                            <input type="hidden" name="tour_title" value="${planner.tour_title}">
                            <button type="submit" class="planner-button">삭제</button>
                        </form>
                    </div>
                </c:forEach>
            </table>
        </div>
    </div>
</div>