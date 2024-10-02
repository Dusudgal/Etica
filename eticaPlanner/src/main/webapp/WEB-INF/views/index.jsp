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
            <h3> Etica에 오신것을 환영합니다. </h3>
            여행 플랜을 만드시려면 >> <a href="/Planner/PlannerPage">Planner 버튼</a>
        </div>
        <div id="imageContainer">
            <img id="slideshowImage" src="" alt="Slideshow Image">
        </div>
    </section>
    <jsp:include page="./include/sidebar.jsp" />
    <footer>
        <jsp:include page="./include/footer.jsp" />
    </footer>
</body>

<%String[] images = {
        request.getContextPath() + "/Img/Eti_sli0.jpg",
        request.getContextPath() + "/Img/Eti_sli1.jpg",
        request.getContextPath() + "/Img/Eti_sli2.jpg",
        request.getContextPath() + "/Img/Eti_sli3.jpg",
        request.getContextPath() + "/Img/Eti_sli4.jpg"};%>
<script>
    const images = [
        '<%= images[0] %>',
        '<%= images[1] %>',
        '<%= images[2] %>',
        '<%= images[3] %>',
        '<%= images[4] %>'
    ];

    // 이미지 슬라이드쇼 로직
    let currentIndex = 0;
    const imageContainer = document.getElementById('slideshowImage'); // 이미지 표시할 div

    function showNextImage() {
        imageContainer.src = images[currentIndex];
        currentIndex = (currentIndex + 1) % images.length;
    }

    setInterval(showNextImage, 3000); // 3초마다 이미지 변경
</script>


</html>