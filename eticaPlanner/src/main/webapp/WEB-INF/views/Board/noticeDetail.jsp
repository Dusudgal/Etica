<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${memo.title}</title>
    <link rel="stylesheet" href="<c:url value='/Resources/css/noticeDetail.css' />" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>공지사항 게시판</h1>
    <div class="memo-content">
        <ul class="list-group">
            <li class="list-group-item"><strong>제목:</strong> <span>${memo.title}</span></li>
            <li class="list-group-item"><strong>작성자:</strong> <span>${memo.username}</span></li>
            <li class="list-group-item"><strong>작성 시간:</strong>
                <span>
                    <fmt:parseDate value="${memo.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDateTime" type="both" />
                    <fmt:formatDate value="${parsedDateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                </span>
            </li>
        </ul>
        <div class="memo-contents">
            <strong>내용:</strong>
            <p>${memo.contents}</p>
        </div>
    </div>
    <a href="/board" class="btn btn-primary">목록으로 돌아가기</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
