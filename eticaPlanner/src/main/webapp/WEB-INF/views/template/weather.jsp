<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="weather-container">
    <c:set var="locations" value="${['서울', '부산', '대구', '인천', '광주', '대전', '울산', '경기', '강원', '충북', '충남', '전남', '경북', '경남', '제주']}"/>
    <c:forEach var="loc" items="${locations}">
        <c:forEach var="data" items="${weatherData}">
            <c:if test="${data.location.trim() eq loc.trim()}">
                <div class="weather-item">
                    <div class="weather-header">${data.location}</div>
                    <div>온도: ${data.temperature}℃</div>
                    <div>강수: ${data.precipitation}%</div>
                </div>
            </c:if>
        </c:forEach>
    </c:forEach>
</div>