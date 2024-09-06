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
        <h2>로그인</h2>
        <form id="loginForm" method="post" action="/user/sign-in">
            <input type="text" name="user_id" placeholder="아이디를 입력해주세요.">
            <input type="password" name="user_password" placeholder="비밀번호를 입력해주세요.">
            <label for="remember-check">
                <input type="checkbox" id="remember-check">아이디 저장하기
                <a href="/user/sign-up-view" class="goToJoin ml-5">회원가입 하러가기</a>
            </label>
            <input type="submit" value="login">
        </form>
    </div>
</div>
</body>
<script>
    $(document).ready(function (){
        $("#loginForm").on('submit', function (e){
            e.preventDefault();
            //alert("로그인");
            let user_id = $("input[name=user_id]").val().trim();
            let user_password = $("input[name=user_password]").val();

            if(!user_id){
                alert("아이디를 입력해주세요.");
                return false;
            }
            if(!user_password){
                alert("비밀번호를 입력해주세요.");
                return false;
            }

            let url = $(this).attr('action');
            console.log(url);
            let params = $(this).serialize();
            console.log(params);

            $.post(url, params) // request
            .done(function (data){ // response
                if(data.code === 200){
                    // 로그인 성공시 이동할 곳
                    location.href = "/";
                    // 아직 구현이 덜 돼서 제자리걸음...
                    //location.reload();
                } else {
                    alert(data.error_message);
                }
            });
        });
    })
</script>
</html>