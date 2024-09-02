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
    <jsp:include page="/WEB-INF/views/MyPage/js/myPage_js.jsp" />
  </head>
  <body>
    <div class="main-container">
      <!-- <div class="benner">
                <h1>Etica</h1>
            </div> -->
      <div class="menu-container">
        <div class="hamburger-menu" id="hamburger-menu">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>
      <nav class="dropdown-menu" id="dropdown-menu">
        <ul>
          <li><a href="/">Home</a></li>
          <li><a href="#">About</a></li>
          <li><a href="#">Services</a></li>
          <li><a href="#">Contact</a></li>
        </ul>
      </nav>
    </div>
    <div class="firstul">
      <ul class="mypagecontents">
        <li class="mypagecontentsli">내 정보 수정</li>
        <li class="mypagecontentsli">비밀번호 변경</li>
        <li class="mypagecontentsli">여행 플래너 보기</li>
      </ul>
    </div>
    <div class="firstul-information">
      <!-- 내 정보 수정 섹션 -->
      <div id="change-information" class="information-section">
        <div class="middle-container">
          <table class="middle-table">
            <tr>
              <th>아이디</th>
              <td>1234</td>
            </tr>
            <tr>
              <th>비밀번호</th>
              <td>1234</td>
            </tr>
            <tr>
              <th>닉네임</th>
              <td>1234</td>
            </tr>
          </table>
        </div>
      </div>

      <!-- 비밀번호 변경 섹션 -->
      <div id="change-password" class="information-section">
        <div class="middle-container">
          <div class="tabletr">
            <input type="password" placeholder="현재 비밀번호" /><br />
            <input type="password" placeholder="변경 비밀번호" /><br />
            <input type="password" placeholder="비밀번호 확인" /><br />
          </div>
        </div>
      </div>

      <!-- 여행 플래너 보기 섹션 -->
      <div id="travel-planner" class="information-section">
        <div class="middle-container">
          <h2>여행 플래너</h2>
        </div>
      </div>
    </div>
    
  </body>
</html>
