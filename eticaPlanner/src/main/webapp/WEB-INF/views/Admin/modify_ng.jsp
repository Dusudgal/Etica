<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/modify_ng.css'/>" type="text/css">
    <title>관리자 정보 수정 실패</title>
</head>
<body>
    <div class="container">
    <h2>관리자 정보 수정에 실패하였습니다.</h2>
    <c:if test="${!empty errorMsg}">
        <p class="error">${errorMsg}</p>
    </c:if>
    <div class="menu">
        <ul>
            <li><a href="<c:url value='/Admin/modifyprofile'/>">다시 시도 하기</a><li>
        </ul>
    </div>
    </div>
</body>
</html>