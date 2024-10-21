<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 재설정 페이지</title>
    <link rel="stylesheet" href="<c:url value='/Resources/css/resetPassword.css' />" type="text/css">
</head>
<body>
<div id="resetSection">
    <h2>비밀번호 재설정</h2>
    <form id="resetForm">
        <input type="hidden" name="token" value="${token}">
        <div>
            <label for="newPassword">새로운 비밀번호:</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>
        <div>
            <label for="confirmPassword">비밀번호 확인:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <button type="submit">비밀번호 재설정</button>
    </form>
</div>
<script>
    $("#resetForm").on("submit", function(e) {
        e.preventDefault();
        let newPassword = $("#newPassword").val().trim();
        let confirmPassword = $("#confirmPassword").val().trim();
        let token = $("input[name='token']").val(); // hidden input에서 token 가져오기

        // 비밀번호 정규식 검사
        let regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()])[a-zA-Z\d!@#$%^&*()]{8,12}$/;
        // 비밀번호 유효성 검사
        if(!regex.test(newPassword)) {
            alert("비밀번호는 8자 이상 12자 이하이며, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.");
            return false;
        }

        if(newPassword !== confirmPassword){
            alert("비밀번호가 일치하지않습니다.")
            return false;
        }

        $.post("/user/reset-password/confirm", { "token": token, "newPassword": newPassword, "confirmPassword": confirmPassword })
            .done(function(data) {
                if (data.code === 200) {
                    alert("비밀번호가 성공적으로 재설정되었습니다.");
                    window.location.href = "/user/sign-in-view"; // 로그인 페이지로 리디렉션
                } else {
                    alert(data.error_message);
                }
            })
            .fail(function() {
                alert("서버 요청에 실패했습니다.");
            });
    });
</script>
</body>
</html>