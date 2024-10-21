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
        <div class="header" style="display: flex; justify-content: space-between; align-items: center;">
            <h2>여행지 목록</h2>
            <div class="button-group" style="display: flex; gap: 10px;">
                <a href="#" class="add-button" onclick="executeTask()">관광지 불러오기</a>
                <a href="<c:url value='/Admin/add_travel'/>" class="add-button">추가</a>
            </div>
        </div>
        <div class="search-container">
            <form action="<c:url value='/Admin/search_travel'/>" method="get" class="search-form">
                <input type="text" name="query" placeholder="검색어를 입력하세요" value="${query}" required class="search-input"/>
                <button type="submit" class="search-button">검색</button>
            </form>
        </div>
        <div class="travel-container">
            <c:choose>
                <c:when test="${not empty travels}">
                    <c:forEach var="travel" items="${travels}">
                        <div class="travel-box">
                            <div class="travel-content">
                                <h3>${travel.tour_title}</h3>
                                <p>${travel.tour_addr}</p>
                            </div>
                            <div class="button-container">
                                <form action="<c:url value='/Admin/edit_travel'/>" method="get">
                                    <input type="hidden" name="id" value="${travel.tour_no}"/>
                                    <button type="submit" class="edit-button">수정</button>
                                </form>
                                <form action="<c:url value='/Admin/delete_travel'/>" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="${travel.tour_no}"/>
                                    <button type="submit" class="delete-button" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>검색 결과가 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>
<!-- 페이지네이션 -->
<div class="pagination" style="display: flex; justify-content: center; margin-top: 20px;">
    <ul style="display: flex; list-style-type: none; align-items: center; padding: 0;">
        <c:if test="${currentPage > 1}">
            <li style="margin-right: 10px;">
                <a href="<c:url value='/Admin/list_travel?page=${currentPage - 1}&query=${query}'/>">이전</a>
            </li>
        </c:if>

        <li style="margin: 0 10px;">
            (${currentPage} / ${totalPages})
        </li>

        <c:if test="${currentPage < totalPages}">
            <li style="margin-left: 10px;">
                <a href="<c:url value='/Admin/list_travel?page=${currentPage + 1}&query=${query}'/>">다음</a>
            </li>
        </c:if>
    </ul>
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