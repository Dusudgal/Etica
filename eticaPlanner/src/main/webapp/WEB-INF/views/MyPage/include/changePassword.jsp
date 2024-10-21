<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form method="post" action="/MyPage/mypage">
    <div id="change-password-section" class="information-section-unique">
        <div class="middle-container">
            <div class="form-row">
                <input type="password" name="currentPassword" placeholder="현재 비밀번호" ><br>
                <input type="password" name="newPassword" placeholder="변경 비밀번호" ><br>
                <input type="password" name="passwordConfirm" placeholder="비밀번호 확인" ><br>
                <button type="submit">비밀번호 변경</button>
            </div>
        </div>
    </div>
</form>