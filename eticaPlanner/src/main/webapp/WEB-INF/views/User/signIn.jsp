<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="KR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 페이지</title>
    <link rel="stylesheet" href="<c:url value='/Resources/css/signInStyle.css' />" type="text/css">
</head>
<body>
<div class="d-flex justify-content-center">
    <div class="login-wrapper">
        <form id="loginForm" method="post" action="/user/sign-in">
            <input type="text" name="user_id" placeholder="아이디를 입력해주세요.">
            <input type="password" name="user_password" placeholder="비밀번호를 입력해주세요.">
            <div class="links d-flex justify-content-center">
                <a href="/user/sign-up-view">회원가입</a>
                <a href="/user/find-password-view" class="ml-5">비밀번호 찾기</a>
                <a href="/user/find-id-view" class="ml-5">아이디 찾기</a>
            </div>
            <input type="submit" value="로그인">
            <button type="button" id="kakaoLoginButton">
                <img src="<c:url value='/Resources/kakao_login_medium_wide.png' />" alt="카카오 로그인" width="320px">
            </button>
        </form>
    </div>
</div>
</body>
<script>
    $(document).ready(function (){
        // 일반 로그인 버튼
        $("#loginForm").on('submit', function (e){
            e.preventDefault();
            //alert("로그인");

            // 폼 데이터 JSON으로 변환
            let form_data = {
                user_id : $("input[name=user_id]").val().trim(),
                user_password : $("input[name=user_password]").val(),
            };

            if (!form_data.user_id) {
                alert("아이디를 입력해 주세요.");
                return false;
            }
            if (!form_data.user_password) {
                alert("비밀번호를 입력해 주세요.");
                return false;
            }

            // AJAX: 화면이동X, 응답값 JSON
            let url = $(this).attr('action');
            console.log(url);

            $.ajax({
                url: url,
                type: 'POST',
                contentType: 'application/json', // 서버가 JSON 형식으로 데이터를 받도록 지정
                data: JSON.stringify(form_data), // JSON 문자열로 변환
                success: function(data) {
                    // {"code":200, "result":"성공"}
                    if (data.code === 200) {
                        alert("로그인 성공! 환영합니다.");
                        location.href = "/"; // 메인페이지로 이동
                    } else if(data.code === 401){
                        alert("비밀번호를 다시 확인해주세요.");
                        location.reload();
                    } else if(data.code === 404){
                        alert("아이디를 다시 확인해주세요.")
                        location.reload();
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error("Request failed:", textStatus, errorThrown);
                    alert("서버 요청에 실패했습니다.");
                    location.reload();
                }
            });
        });
        // 카카오로그인 버튼
        $("#kakaoLoginButton").on('click', function () {
            fetch('/user/kakao-login')
                .then(response => response.json())
                .then(data => {
                    window.location.href = data.kakaoLoginUrl;
                })
                .catch(error => console.error('Error:', error));
        });
    });
</script>
</html>