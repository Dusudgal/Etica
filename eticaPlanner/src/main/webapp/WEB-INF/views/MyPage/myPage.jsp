<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <title>MyPage</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/Resources/css/myPage.css"
    />
  </head>
  <body>
    <div class="firstul">
      <ul class="mypagecontents">
        <li class="mypagecontentsli">내 정보</li>

            <li class="mypagecontentsli">비밀번호 변경</li>

        <li class="mypagecontentsli">여행 플래너 보기</li>
        <li class="mypagecontentsli">임시</li>
      </ul>
    </div>
    <div class="container">
      <div class="firstul-information">
        <jsp:include page="include/lookInformation.jsp" />
        <jsp:include page="include/changePassword.jsp" />
        <jsp:include page="include/travelPlanner.jsp" />
        <jsp:include page="include/imsy.jsp" />
      </div>
    </div>


    <jsp:include page="/WEB-INF/views/MyPage/js/myPage_js.jsp" />
  </body>
</html>
