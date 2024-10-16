<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="travel-planner" class="information-section">
    <div class="middle-container">
        <h2>여행 플래너 목록</h2>
        <table>
        <div class="planner-list">
            <c:forEach var="planner" items="${plannerList}">
                <tr>
                    <td>${planner.tour_title}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Planner/ViewPlan" method="POST">
                            <input type="hidden" name="tour_title" value="${planner.tour_title}">
                            <input type="hidden" name="planNo" value="${planner.planNo}">
                            <button type="submit">조회</button>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Planner/ModifyPlan" method="POST">
                            <input type="hidden" name="tour_title" value="${planner.tour_title}">
                            <input type="hidden" name="planNo" value="${planner.planNo}">
                            <button type="submit">수정</button>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Planner/DeletePlan" method="POST">
                            <input type="hidden" name="planNo" value="${planner.planNo}">
                            <button type="submit" onclick="return confirm('정말로 삭제하시겠습니까?');">삭제</button>
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>