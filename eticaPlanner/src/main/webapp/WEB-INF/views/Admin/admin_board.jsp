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
        h2 {
            color: #00BFFF; /* 제목 색상 */
            text-align: center;
            margin-top: 70px;
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
            border: 1px solid #00BFFF; /* 테두리 색상 */
        }
        th {
            background-color: #00BFFF; /* 헤더 배경색 */
            color: #fff; /* 헤더 글자 색상 */
        }
        tr:nth-child(even) {
            background-color: #F0F8FF; /* 짝수 행 배경색 */
        }
        tr:hover {
            background-color: #87CEFA; /* 행 호버 효과 */
            color: #black; /* 호버 시 글자 색상 */
        }
        a {
            color: #d5006d; /* 링크 색상 */
            text-decoration: none; /* 링크 밑줄 제거 */
        }
        a:hover {
            text-decoration: underline; /* 링크 호버 시 밑줄 추가 */
        }

        .btn-write { /* 작성하기 버튼 스타일 */
             display: block;
             width: 150px;
             margin: 20px auto;
             padding: 10px 0;
             background-color: #00BFFF;
             color: white;
             text-align: center;
             border-radius: 5px;
             text-decoration: none;
        }

        .btn-write:hover {
            background-color: #ff8b8b;
        }
    </style>
</head>
<body>
<jsp:include page="login_ok.jsp"/>
<div class="container">
    <h2>게시판 목록</h2>
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
    <a href="/admin/notice" class="btn-write">작성 및 수정</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
