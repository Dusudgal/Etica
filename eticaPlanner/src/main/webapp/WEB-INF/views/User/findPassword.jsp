<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기 페이지</title>
    <link rel="stylesheet" href="<c:url value='/Resources/css/findPassword.css' />" type="text/css">
</head>
<body>
<!-- 비밀번호 찾기 폼 -->
<div id="resetPasswordSection">
    <h2>비밀번호 찾기</h2>
    <form id="resetPasswordForm">
        <input type="text" id="reset_email" placeholder="이메일을 입력하세요" required />
        <button type="submit">이메일 인증</button>
        <div id="reset_result"></div>
    </form>
</div>
<script>
    $("#resetPasswordForm").on("submit", function(e) {
        e.preventDefault();
        let email = $("#reset_email").val().trim();

        // 사용자에게 처리 중임을 알림
        $("#reset_result").text("이메일 확인 중입니다...");

        $.post("/user/reset-password/link", {"email": email})
            .done(function(data) {
                if (data.code === 200) {
                    $("#reset_result").text("재설정 링크가 이메일로 전송되었습니다.");
                } else {
                    alert(data.error_message);
                }
            })
            .fail(function() {
                alert("이메일을 다시 확인해주세요.");
            });
    });
</script>
</body>
</html>