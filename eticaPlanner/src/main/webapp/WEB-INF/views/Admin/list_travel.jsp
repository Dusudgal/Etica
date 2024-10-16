<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/list_travel.css'/>" type="text/css">
    <title>여행지목록페이지</title>
</head>
<body>
    <jsp:include page="login_ok.jsp"/>
    <section class="travel-list">
        <div class="header">
            <h2>여행지 목록</h2>
            <a href="#" class="add-button" onclick="executeTask()">관광지 불러오기</a>
            <a href="<c:url value='/Admin/add_travel'/>" class="add-button">추가</a>
        </div>
        <div class="travel-container">
            <c:forEach var="travel" items="${travels}">
                <div class="travel-box">
                    <div class="travel-content">
                        <h3>${travel.travelName}</h3>
                        <p>${travel.travelText}</p>
                    </div>
                    <div class="button-container">
                        <form action="<c:url value='/Admin/edit_travel'/>" method="get">
                            <input type="hidden" name="id" value="${travel.travelNo}"/>
                            <button type="submit" class="edit-button">수정</button>
                        </form>
                        <form action="<c:url value='/Admin/delete_travel'/>" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${travel.travelName}"/>
                            <button type="submit" class="delete-button" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
    <script>
    async function executeTask() {
        try {
            const response = await fetch('<c:url value="/Admin/tour_api"/>', {
                method: 'POST',
            });

            if (response.ok) {
                console.log("Task executed successfully");
            } else {
                console.error("Error executing task");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    }
    </script>
</body>
</html>