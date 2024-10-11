<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/add_travel.css'/>" type="text/css">
    <title>여행지추가페이지</title>
</head>
<body>
    <jsp:include page="login_ok.jsp"/>
    <div class ="layout-wrapper">
        <div class ="map-placeholder" id="map">
            지도
        </div>
        <div class="form-container">
            <form method="post" action="<c:url value='/Admin/add_travel'/>" id="travelform">
                <div>
                    <label for="travel_name">여행지 이름</label>
                    <input type="text" id="travel_name" name="travel_name">
                </div>
                <div>
                    <label for="travel_context">여행지 설명</label>
                    <textarea type="text" id="travel_context" name="travel_context"></textarea>
                </div>
                <div>
                    <label for="travel_X-marker">여행지 X좌표</label>
                    <input type="text" id="travel_X_marker" name="travel_X_marker">
                </div>
                <div>
                    <label for="travel_Y-marker">여행지 Y좌표</label>
                    <input type="text" id="travel_Y_marker" name="travel_Y_marker">
                </div>
            </form>
            <div class="button-container">
                <button type="submit" form="travelform" class="add-button">추가</button>
                <button type="submit" onclick="history.back()" class="cancel-button">취소</button>
            </div>
        </div>
    </div>
    <jsp:include page="add_travel_js.jsp"/>
</body>
</html>