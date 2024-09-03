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
      <nav id="dropdown-menu">
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
        <li class="mypagecontentsli">임시</li>
      </ul>
    </div>
    <div class="container">
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
          <form id="signUpForm" method="post">
            <div class="header">
              <div class="mb-3">여행 플래너</div>
            </div>
            <div class="user-info">
              <div class="user-info-item">
                <div class="d-flex align-items-center mb-2">
                  <div class="mb-2">여</div>
                  <input />
                </div>
              </div>
              <div class="user-info-item">
                <div class="mb-2">행</div>
                <input />
              </div>
              <div class="user-info-item">
                <div class="mb-2">플</div>
                <input />
              </div>
              <div class="user-info-item">
                <div class="mb-2">래</div>
                <input />
              </div>
              <div class="user-info-item">
                <div class="mb-2">너</div>
                <input />
              </div>
              <div class="user-info-item">
                <div class="mb-2">입</div>
                <input />
              </div>
            </div>
            <button type="submit">니</button>
            <button type="submit">다</button>
          </form>
        </div>

        <!-- 임시 -->
        <div id="member-container" class="information-section">
          <form id="signUpForm" method="post">
            <div class="header">
              <div class="mb-3">회원 정보를 입력해주세요</div>
            </div>
            <div class="user-info">
              <div class="user-info-item">
                <div class="d-flex align-items-center mb-2">
                  <div class="label pt-4">아이디(4자이상)</div>
                  <button
                    type="button"
                    id="loginIdCheckBtn"
                    class="btn btn-check"
                  >
                    중복확인
                  </button>
                </div>
                <div class="input-wrapper">
                  <input
                    type="text"
                    id="loginId"
                    name="loginId"
                    class="form-control"
                    placeholder="아이디를 입력해주세요."
                  />
                  <div id="idCheckLength" class="feedback text-danger d-none">
                    ID를 4자 이상 입력해주세요.
                  </div>
                  <div
                    id="idCheckDuplicated"
                    class="feedback text-danger d-none"
                  >
                    이미 사용중인 ID입니다.
                  </div>
                  <div id="idCheckOk" class="feedback text-success d-none">
                    사용 가능한 ID입니다.
                  </div>
                </div>
              </div>
              <div class="user-info-item">
                <div class="mb-2">비밀번호</div>
                <input
                  type="password"
                  id="password"
                  name="password"
                  class="form-control"
                  placeholder="비밀번호를 입력해주세요."
                />
              </div>
              <div class="user-info-item">
                <div class="mb-2">비밀번호 확인</div>
                <input
                  type="password"
                  id="confirmPassword"
                  class="form-control"
                  placeholder="비밀번호를 입력해주세요."
                />
              </div>
              <div class="user-info-item">
                <div class="mb-2">이름</div>
                <input
                  type="text"
                  id="name"
                  name="name"
                  class="form-control"
                  placeholder="이름을 입력해주세요."
                />
              </div>
              <div class="user-info-item">
                <div class="mb-2">핸드폰 번호(11자)</div>
                <input
                  type="text"
                  id="phone"
                  name="phone"
                  class="form-control"
                  placeholder="핸드폰 번호를 입력해주세요."
                />
              </div>
              <div class="user-info-item">
                <div class="mb-2">생년월일(YYMMDD)</div>
                <input
                  type="text"
                  id="birth"
                  name="birth"
                  class="form-control"
                  placeholder="생년월일을 입력해주세요."
                />
              </div>
            </div>
            <div class="gender">
              <input type="radio" id="male" name="gender" value="male" />
              <label for="male">남성</label>
              <input type="radio" id="female" name="gender" value="female" />
              <label for="female">여성</label>
            </div>
            <div class="agree-check">
              <input type="checkbox" id="agree" />
              <label for="agree"
                >이용약관 개인정보 수집 및 이용, 마케팅 활용 선택에 모두
                동의합니다.</label
              >
            </div>
            <button type="submit">가입하기</button>
          </form>
        </div>
      </div>
    </div>

    <jsp:include page="/WEB-INF/views/MyPage/js/myPage_js.jsp" />
  </body>
</html>
