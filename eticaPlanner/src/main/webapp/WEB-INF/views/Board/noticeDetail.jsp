<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${memo.title}</title>
    <style>
        body {
            background-color: #ffffff; /* 배경색 하얀색 */
        }
        h1 {
            color: #d5006d; /* 제목 색상 */
            text-align: center;
            margin-top: 20px;
        }
        .memo-content {
            margin: 20px;
            padding: 20px;
            border: 1px solid #d5006d; /* 테두리 색상 */
            border-radius: 8px;
            background-color: #ffffff; /* 내용 배경색 */
        }
        .list-group-item {
            border: none;
            padding-left: 0;
            font-weight: bold;
        }
        .list-group-item span {
            font-weight: normal;
        }
        .memo-contents {
            margin-top: 20px;
            padding: 10px;
            border-radius: 5px;
            background-color: #f9f9f9;
            border: 1px solid #ddd ;
        }
        .list-title > span{
            margin : 10px;
        }
        .container{
            display: flex;
            flex-direction: column;
        }

    </style>
</head>
<body>
<div class="container">
    <h1>공지사항 게시판</h1>
    <div class="memo-content">
        <div class="list-group">
            <div class="list-title">
                <strong>제목:</strong> <span>${memo.title}</span>
                <strong>작성자:</strong> <span>${memo.username}</span>
                <strong>작성 시간:</strong>
                <span>
                    <fmt:parseDate value="${memo.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDateTime" type="both" />
                    <fmt:formatDate value="${parsedDateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                </span>
            </div>
        </div>
        <div class="memo-contents">
            <strong>내용:</strong>
            <p>${memo.contents}</p>
        </div>
    </div>
    <a href="/board" class="btn">목록으로 돌아가기</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
</body>
</html>
