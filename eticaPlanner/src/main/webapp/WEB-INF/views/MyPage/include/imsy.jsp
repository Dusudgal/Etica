<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<div id="member-container" class="information-section">
  <form id="signUpForm" method="post">
    <div class="header">
      <div class="mb-3">회원 정보를 입력해주세요</div>
    </div>
    <div class="user-info">
      <div class="user-info-item">
        <div class="d-flex align-items-center mb-2">
          <div class="label pt-4">아이디(4자이상)</div>
          <button type="button" id="loginIdCheckBtn" class="btn btn-check">
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
          <div id="idCheckDuplicated" class="feedback text-danger d-none">
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
