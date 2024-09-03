<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 페이지</title>
    <link rel="stylesheet" href="<c:url value='/Resources/css/signUpStyle.css' />" type="text/css">
</head>
<body>
<div class="container">
    <div class="member-container">
        <form id="signUpForm" method="post" action="/user/sign-up">
            <div class="header">
                <div class="mb-3">회원 정보를 입력해주세요</div>
            </div>
            <div class="user-info">
                <div class="user-info-item">
                    <div class="d-flex align-items-center mb-2">
                        <div class="label pt-4">아이디(4자이상)</div>
                        <button type="button" id="loginIdCheckBtn" class="btn btn-check">중복확인</button>
                    </div>
                    <div class="input-wrapper">
                        <input type="text" id="loginId" name="loginId" class="form-control" placeholder="아이디를 입력해주세요." />
                        <div id="idCheckLength" class="feedback text-danger d-none">ID를 4자 이상 입력해주세요.</div>
                        <div id="idCheckDuplicated" class="feedback text-danger d-none">이미 사용중인 ID입니다.</div>
                        <div id="idCheckOk" class="feedback text-success d-none">사용 가능한 ID입니다.</div>
                    </div>
                </div>
                <div class="user-info-item">
                    <div class="mb-2">비밀번호</div>
                    <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호를 입력해주세요." />
                </div>
                <div class="user-info-item">
                    <div class="mb-2">비밀번호 확인</div>
                    <input type="password" id="confirmPassword" class="form-control" placeholder="비밀번호를 입력해주세요." />
                </div>
                <div class="user-info-item">
                    <div class="mb-2">이름</div>
                    <input type="text" id="name" name="name" class="form-control" placeholder="이름을 입력해주세요." />
                </div>
                <div class="user-info-item">
                    <div class="mb-2">핸드폰 번호(11자)</div>
                    <input type="text" id="phone" name="phone" class="form-control" placeholder="핸드폰 번호를 입력해주세요." />
                </div>
                <div class="user-info-item">
                    <div class="mb-2">생년월일(YYMMDD)</div>
                    <input type="text" id="birth" name="birth" class="form-control" placeholder="생년월일을 입력해주세요." />
                </div>
            </div>
            <div class="gender">
                <input type="radio" id="male" name="gender" value="male"/>
                <label for="male">남성</label>
                <input type="radio" id="female" name="gender" value="female"/>
                <label for="female">여성</label>
            </div>
            <div class="agree-check">
                <input type="checkbox" id="agree"/>
                <label for="agree">이용약관 개인정보 수집 및 이용, 마케팅 활용 선택에 모두 동의합니다.</label>
            </div>
            <button type="submit">가입하기</button>
        </form>
    </div>
</div>
</body>

<script>
    $(document).ready(function(){

        // 아이디 중복확인
        $("#loginIdCheckBtn").on('click', function (){
           //alert("중복확인");

            // 경고문구 초기화
            $("#idCheckLength").addClass("d-none");
            $("#idCheckDuplicated").addClass("d-none");
            $("#idCheckOk").addClass("d-none");

            let loginId = $("#loginId").val().trim();
            if(loginId.length < 4){
                $("#idCheckLength").removeClass("d-none");
                return;
            }

            // AJAX - 중복확인
            $.get("/user/is-duplicated-id", {"user_id":loginId}) // request
            .done(function(data){ // response
                if(data.code === 200){
                    if(data.is_duplicated_id){ // 중복
                        $("#idCheckDuplicated").removeClass("d-none");
                    } else { // 사용가능
                        $("#idCheckOk").removeClass("d-none");
                    }
                } else {
                    alert(data.error_message);
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                    console.error("Request failed:", textStatus, errorThrown);
                    alert("서버 요청에 실패했습니다.");
            });
        });

        // 회원가입
        $('#signUpForm').on('submit', function(e){
            e.preventDefault();
           //alert("회원가입");

            //validation check
            let loginId = $("#loginId").val().trim();
            let password = $("#password").val();
            let confirmPassword = $("#confirmPassword").val();
            let name = $("#name").val().trim();
            let phone = $("#phone").val().trim();
            let birth = $("#birth").val().trim();
            let gender = $("input[name='gender']:checked").val();
            let agree = $("#agree").prop('checked');

            if(!loginId){
                alert("아이디를 입력해주세요.");
                return false;
            }
            if(!password || !confirmPassword){
                alert("비밀번호를 입력해주세요.");
                return false;
            }
            if(password !== confirmPassword){
                alert("비밀번호가 일치하지않습니다.")
                return false;
            }
            if(!name){
                alert("이름을 입력해주세요.");
                return false;
            }
            if(!phone){
                alert("핸드폰 번호를 입력해주세요.");
                return false;
            }
            if(phone.length !== 11){
                alert("올바른 핸드폰 번호 11자리를 입력해주세요.")
                return false;
            }
            if(!birth){
                alert("생년월일(YYMMDD)을 입력해주세요.");
                return false;
            }
            if(birth.length !== 6){
                alert("올바른 생년월일(YYMMDD) 6자리를 입력해주세요.)
                return false;
            }
            if(!gender){
                alert("성별을 선택해주세요.")
                return false;
            }
            if(!agree){
                alert("이용약관에 동의해주세요.");
                return false;
            }

            // 중복 확인 후 사용 가능한 아이디인지 확인
            if($("#idCheckOk").hasClass('d-none')){
                alert("아이디 중복확인을 다시 해주세요.");
                return false;
            }
            //alert("회원가입 완료");

            // AJAX: 화면이동X, 응답값 JSON
            let url = $(this).attr("action");
            let params = $(this).serialize(); // 폼태그에 있는 name 속성과 값으로 파라미터 구성
            console.log(params);

            $.post(url, params) // request
            .done(function (data){ // response
                // {"code":200, "result":"성공"}
                if(data.code === 200){
                    alert("가입을 환영합니다. 로그인 해주세요.");
                    location.href = "/user/sign-in-view";
                } else {
                    alert(data.error_message);
                }
            });
        });

    })
</script>
</html>