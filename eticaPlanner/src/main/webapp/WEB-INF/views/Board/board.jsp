<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- 올바른 CSS 파일 경로 설정 -->
    <link rel="stylesheet" href="<c:url value='/Resources/css/boardPage.css' />" type="text/css">
    <!-- 외부 CSS 파일 링크 -->
    <title>메모 게시판</title>
    <style>
        .container {
            display : block
        }
    </style>
</head>
<body>
<div class="container">
    <h1>공지사항 게시판</h1>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>제목</th>
            <th>작성자</th>
            <th>작성 시간</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="memo" items="${notices}">
            <tr>
                <td><a href="/board/notice/${memo.id}" class="text-primary">${memo.title}</a></td>
                <td>${memo.username}</td>
                <td>${memo.createdAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <!-- 이전 페이지로 이동 -->
            <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>'">
                <a class="page-link" href="?page=${currentPage - 1}" tabindex="-1">이전</a>
            </li>

            <!-- 페이지 번호 출력 -->
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item <c:if test='${currentPage == i}'>active</c:if>'">
                    <a class="page-link" href="?page=${i}">${i}</a>
                </li>
            </c:forEach>

            <!-- 다음 페이지로 이동 -->
            <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>'">
                <a class="page-link" href="?page=${currentPage + 1}">다음</a>
            </li>
        </ul>
    </nav>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</body>
</html>
