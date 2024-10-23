<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/MainIndex.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/css/noticePopUp.css' />">
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
    <!-- 공지사항 팝업 -->
    <div id="noticePopup" style="display:none;">
        <h2>공지사항</h2>
        <c:choose>
            <c:when test="${not empty notice}">
                <p><strong>제목:</strong> <c:out value="${notice.title}" /></p>
                <p><c:out value="${notice.contents}" /></p>
            </c:when>
            <c:otherwise>
                <p>공지사항이 없습니다.</p>
            </c:otherwise>
        </c:choose>
        <button class="popup-close-btn" onclick="closeNoticePopup()">닫기</button>
        <span class="popup-no-show-24" onclick="setCookieFor24Hours()">24시간 동안 보지 않기</span>
    </div>
    <jsp:include page="./template/weather.jsp" />
    <section class="contents my-5">
        <div class="container-fluid">
            <div class="planner-container">
                <h2>Eitca 플래너에 오신 것을 환영합니다!</h2>
                    <br><br>
                    <p>Eitca 플래너는 여러분의 국내 여행을 편하게 만들기 위해 만들어진 플랫폼입니다.<br>
                       쉽고 직관적인 인터페이스를 통해 원하는 여행지를 검색하고,<br>
                       나만의 여행 플랜을 간편하게 작성할 수 있습니다.<br>
                       다양한 관광지에 대한 사용자 리뷰를 통해 다른 여행자들의 경험을 참고하고,<br>
                       더욱 알찬 여행을 계획해 보세요.<br>
                       Eitca 플래너는 여러분의 여행을 더욱 특별하게 만들어 줄 것입니다.<br>
                       여러분의 꿈꾸는 여행을 Eitca 플래너와 함께 시작해 보세요!<br></p>
                <div class="planner-div">
                    <p>Etica를 통해 여행 계획을 스케줄링 해보세요.</p>
                    <a href="/Planner/PlannerPage" class="button">시작하기</a>
                </div>
            </div>
            <div id="imageContainer">
                <img id="slideshowImage" src="" alt="Slideshow Image">
            </div>
        </div>
    </section>
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
    imageContainer.src = images[4];

    setInterval(showNextImage, 3000); // 3초마다 이미지 변경

    // 공지사항 팝업 로직
    const noticePopup = document.getElementById("noticePopup");

    function showNoticePopup() {
        const isPopupDisabled = getCookie("popupDisabled");

        if (!isPopupDisabled && document.querySelector("#noticePopup p")) { // 공지사항이 있을 경우에만 팝업을 띄움
            noticePopup.style.display = "block";
        }
    }

    function closeNoticePopup() {
        noticePopup.style.display = "none";
    }

    function setCookie(name, value, hours) {
        let date = new Date();
        date.setTime(date.getTime() + (hours * 60 * 60 * 1000));
        document.cookie = name + "=" + value + "; expires=" + date.toUTCString() + "; path=/";
    }

    function getCookie(name) {
        let cookieArr = document.cookie.split(";");
        for (let i = 0; i < cookieArr.length; i++) {
            let cookiePair = cookieArr[i].split("=");
            if (name === cookiePair[0].trim()) {
                return cookiePair[1];
            }
        }
        return null;
    }

    function setCookieFor24Hours() {
        setCookie("popupDisabled", "true", 24);
        closeNoticePopup();
    }

    window.onload = showNoticePopup;
</script>


</html>