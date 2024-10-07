<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>메모 게시판</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #ffffff; /* 하얀색 배경 */
        }
        h1 {
            color: #d5006d; /* 제목 색상 */
            text-align: center;
            margin-top: 20px;
        }
        table {
            width: 100%;
            margin-top: 30px;
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            text-align: center;
            padding: 12px;
            border: 1px solid #d5006d; /* 테두리 색상 */
        }
        th {
            background-color: #ff8b8b; /* 헤더 배경색 */
            color: #fff; /* 헤더 글자 색상 */
        }
        tr:nth-child(even) {
            background-color: #f4c3d7; /* 짝수 행 배경색 */
        }
        tr:hover {
            background-color: #ff8b8b; /* 행 호버 효과 */
            color: #fff; /* 호버 시 글자 색상 */
        }
        a {
            color: #d5006d; /* 링크 색상 */
            text-decoration: none; /* 링크 밑줄 제거 */
        }
        a:hover {
            text-decoration: underline; /* 링크 호버 시 밑줄 추가 */
        }
    </style>
</head>
<body>
<jsp:include page="../include/header.jsp"></jsp:include>
<div class="container">
    <h1>메모 게시판</h1>
    <table>
        <thead>
        <tr>
            <th>제목</th>
            <th>작성자</th>
            <th>작성 시간</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="memo" items="${notices}"> <!-- 'memos'를 'notices'로 변경 -->
            <tr>
                <td><a href="/board/notice/${memo.id}" class="text-primary">${memo.title}</a></td> <!-- URL 수정 -->
                <td>${memo.username}</td> <!-- 작성자 출력 -->
                <td>${memo.createdAt}</td> <!-- 작성 시간 출력 -->
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<jsp:include page="../include/footer.jsp"></jsp:include>
</body>
</html>
