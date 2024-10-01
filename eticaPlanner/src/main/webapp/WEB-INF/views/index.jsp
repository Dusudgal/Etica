<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/MainIndex.css' />">

    <%-- bootstrap --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>

    <%-- 내가 만든 스타일시트 --%>
    <link rel="stylesheet" href="<c:url value='/Resources/css/style.css' />" type="text/css">
    <title>Etica Planner</title>
</head>
<body>
    <jsp:include page="./include/header.jsp" />
    <section class="contents my-5">
        <div class="container">
            <h1> hello main index </h1>
            <div class="image-text-container">
                <p>Etica Planner를 찾아주셔서 감사합니다.</p>
            </div>
        </div>
        <div class="button-container">
            <div class="button-container">
                <a href="/Review/ReviewIndex" class="button">Review 버튼</a>
                <a href="/Planner/PlannerIndex" class="button">Planner 버튼</a>
                <a href="Admin/signin" class="button">관리자 버튼</a>
            </div>
        </div>
    </section>
    <footer>
        <jsp:include page="./include/footer.jsp" />
    </footer>
</body>
</html>