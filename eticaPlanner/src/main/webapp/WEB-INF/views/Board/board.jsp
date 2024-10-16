<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>메모 게시판</title>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <style>
        .container{
            display: flex;
            flex-direction: column;
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
            border: 1px solid #000; /* 테두리 색상 */
        }
        th {
            background-color: #fff; /* 헤더 배경색 */
            color: #000; /* 헤더 글자 색상 */
        }
        tr:hover {
            background-color: #ff8b8b; /* 행 호버 효과 */
            color: #fff; /* 호버 시 글자 색상 */
        }
        td > a {
            color: #d5006d; /* 링크 색상 */
            text-decoration: none; /* 링크 밑줄 제거 */
        }
        td > a:hover {
            text-decoration: underline; /* 링크 호버 시 밑줄 추가 */
        }
    </style>
</head>
<body>
<div class="container">
    <h1>공지 사항 게시판</h1>
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
            <tr onclick="location.href='/board/notice/${memo.id}'">
                <td>${memo.title}</td>
                <td>${memo.username}</td> <!-- 작성자 출력 -->
                <td>${memo.createdAt}</td> <!-- 작성 시간 출력 -->
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
