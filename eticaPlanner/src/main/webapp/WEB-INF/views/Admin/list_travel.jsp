<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/add_travel.css'/>" type="text/css">
    <title>여행지목록페이지</title>
</head>
<body>
    <jsp:include page="login_ok.jsp"/>
    <section class="travel-list">
         <a href="<c:url value='/Admin/add_travel'/>">추가</a>
        <h2>여행지 목록</h2>
        <table>
            <thead>
                <tr>
                    <th>여행지 이름</th>
                    <th>여행지 설명</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="travel" items="${travels}">
                    <tr>
                        <td>${travel.travelName}</td>
                        <td>${travel.travelText}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>
</body>