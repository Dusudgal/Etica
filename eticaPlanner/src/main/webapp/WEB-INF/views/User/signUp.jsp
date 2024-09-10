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
                        <button type="button" id="login_id_check_btn" class="btn btn-check">중복확인</button>
                    </div>
                    <div class="input-wrapper">
                        <input type="text" id="user_id" name="user_id" class="form-control" placeholder="아이디를 입력해 주세요." />

                        <div id="id_check_length" class="feedback text-danger d-none">ID를 4자 이상 입력해주세요.</div>
                        <div id="id_check_duplicated" class="feedback text-danger d-none">이미 사용중인 ID입니다.</div>
                        <div id="id_check_ok" class="feedback text-success d-none">사용 가능한 ID입니다.</div>
                    </div>
                </div>
                <div class="user-info-item">
                    <div class="mb-2">비밀번호(8자 이상 12자 이하, 영문 대소문자, 숫자, 특수문자 모두 포함)</div>
                    <input type="password" id="user_password" name="user_password" class="form-control" placeholder="비밀번호를 입력해 주세요" />
                </div>
                <div class="user-info-item">
                    <div class="mb-2">비밀번호 확인</div>
                    <input type="password" id="user_confirm_password" class="form-control" placeholder="비밀번호를 다시 입력해 주세요." />
                </div>
                <div class="user-info-item">
                    <div class="mb-2">이름</div>
                    <input type="text" id="user_name" name="user_name" class="form-control" placeholder="이름을 입력해 주세요." />
                </div>
                <div class="user-info-item">
                    <div class="mb-2">닉네임</div>
                    <input type="text" id="user_nickname" name="user_nickname" class="form-control" placeholder="닉네임을 입력해 주세요." />
                    <div id="nickname_check_result" class="feedback"></div>
                </div>
                <div class="user-info-item">
                    <div class="mb-2">핸드폰 번호(-제외)</div>
                    <input type="text" id="user_phone" name="user_phone" class="form-control" placeholder="핸드폰 번호(010XXXXXXXX)를 입력해 주세요." />
                    <div id="phone_check_result" class="feedback"></div>
                </div>
                <div class="user-info-item">
                    <div class="mb-2">생년월일(YYMMDD)</div>
                    <input type="text" id="user_birth" name="user_birth" class="form-control" placeholder="생년월일을 입력해 주세요." />
                </div>
            </div>
            <div class="user_gender">
                <input type="radio" id="male" name="user_gender" value="male"/>
                <label for="male">남성</label>
                <input type="radio" id="female" name="user_gender" value="female"/>
                <label for="female">여성</label>
            </div>
            <div class="agree-check">
                <input type="checkbox" id="agree"/>
                <label for="agree">개인정보 수집 및 이용에 모두 동의합니다.</label>
            </div>
            <div class="agree-check">
                <input type="checkbox" id="agreeCheck"/>
                <label for="agreeCheck">(선택)마케팅 활용 선택에 모두 동의합니다.</label>
            </div>
            <button type="submit">가입하기</button>
        </form>
    </div>
</div>
</body>

<script>
    $(document).ready(function(){

        // 아이디 중복확인
        $("#login_id_check_btn").on('click', function (){
           //alert("중복확인");

            // 경고문구 초기화
            $("#id_check_length").addClass("d-none");
            $("#id_check_duplicated").addClass("d-none");
            $("#id_check_ok").addClass("d-none");

            let user_id = $("#user_id").val().trim();
            if(user_id.length < 4){
                $("#id_check_length").removeClass("d-none");
                return;
            }

            // AJAX - 중복확인
            $.get("/user/is-duplicated-id", {"user_id":user_id}) // request
            .done(function(data){ // response
                if(data.code === 200){
                    if(data.is_duplicated_id){ // 중복
                        $("#id_check_duplicated").removeClass("d-none");
                    } else { // 사용가능
                        $("#id_check_ok").removeClass("d-none");
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

        // 닉네임 중복확인(버튼x)
        $("#user_nickname").on('input', function (){
            let user_nickname = $("#user_nickname").val().trim();

            if(user_nickname.length === 0){
                $("#nickname_check_result").text('');
                return;
            }

            $.get("/user/is-duplicated-nickname", {"user_nickname":user_nickname}) //request
            .done(function(data){ //response
                if(data.code === 200){
                    if(data.is_duplicated_nickname){ // 중복
                        $("#nickname_check_result").text("이미 사용 중인 닉네임입니다.").css('color', 'red');
                    } else { // 중복이 아니다
                        $("#nickname_check_result").text("사용 가능한 닉네임입니다.").css('color', 'green');
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

        // 핸드폰 번호 중복확인(버튼x)
        $("#user_phone").on('input', function (){
            let user_phone = $("#user_phone").val().trim();

            if(user_phone.length === 0){
                $("#phone_check_result").text('');
                return;
            }

            $.get("/user/is-duplicated-phone",{"user_phone":user_phone}) // request
            .done(function (data){ // response
                if(data.code === 200){
                    if(data.is_duplicated_phone){ // 중복
                        $("#phone_check_result").text("이미 사용 중인 핸드폰 번호입니다.").css('color', 'red');
                    } else { // 중복x
                        $("#phone_check_result").text("사용 가능한 핸드폰 번호입니다.").css('color', 'green');
                    }
                } else {
                    alert(data.error_message);
                }
            })
            .fail(function(jqXHR, textStatus, errorThrown){
                console.error("Request failed:", textStatus, errorThrown);
                alert("서버 요청에 실패했습니다.");
            })
        })
        // 핸드폰 번호에 숫자만 입력되도록 제한
        $("#user_phone").on('input', function() {
            this.value = this.value.replace(/[^0-9]/g, ''); // 숫자만 허용
        });

        // 생년월일에 숫자만 입력되도록 제한
        $("#user_birth").on('input', function() {
            this.value = this.value.replace(/[^0-9]/g, ''); // 숫자만 허용
        });

        // 회원가입
        $('#signUpForm').on('submit', function(e){
            e.preventDefault();
           //alert("회원가입");

            // 폼 데이터 json으로 변환
            //validation check
            let form_data = {
                user_id : $("#user_id").val().trim(),
                user_password : $("#user_password").val(),
                user_confirm_password : $("#user_confirm_password").val(),
                user_name : $("#user_name").val().trim(),
                user_nickname : $("#user_nickname").val().trim(),
                user_phone : $("#user_phone").val().trim(),
                user_birth : $("#user_birth").val().trim(),
                user_gender : $("input[name='user_gender']:checked").val(),
                agree : $("#agree").prop('checked')
            }

            if(!form_data.user_id){
                alert("아이디를 입력해 주세요.");
                return false;
            }
            if(!form_data.user_password || !form_data.user_confirm_password){
                alert("비밀번호를 입력해 주세요.");
                return false;
            }

            // 비밀번호 정규식 검사
            let regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()])[a-zA-Z\d!@#$%^&*()]{8,12}$/;
            // 비밀번호 유효성 검사
            if(!regex.test(form_data.user_password)) {
                alert("비밀번호는 8자 이상 12자 이하이며, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.");
                return false;
            }

            if(form_data.user_password !== form_data.user_confirm_password){
                alert("비밀번호가 일치하지않습니다.")
                return false;
            }
            if(!form_data.user_name){
                alert("이름을 입력해 주세요.");
                return false;
            }
            if(!form_data.user_nickname){
                alert("닉네임을 입력해 주세요.");
                return false;
            }
            if(!form_data.user_phone){
                alert("핸드폰 번호를 입력해 주세요.(-제외)");
                return false;
            }
            if(!/^010/.test(form_data.user_phone)){
                alert("핸드폰 번호는 010으로 시작해야 합니다.");
                return false;
            }
            if(form_data.user_phone.length !== 11){
                alert("올바른 핸드폰 번호 11자리를 입력해 주세요.")
                return false;
            }
            if(!form_data.user_birth){
                alert("생년월일(YYMMDD)을 입력해 주세요.");
                return false;
            }

            if(form_data.user_birth.length !== 6){
                alert("올바른 생년월일(YYMMDD) 6자리를 입력해 주세요.");
                return false;
            }
            // 월과 일 검증
            let month = parseInt(form_data.user_birth.substring(2, 4), 10);
            let day = parseInt(form_data.user_birth.substring(4), 10);

            if (month < 1 || month > 12 || day < 1 || day > 31) {
                alert("월(M)일(D)을 올바른 형식으로 입력하신 후 다시 시도해 주세요.")
                return false;
            }
            if(!form_data.user_gender){
                alert("성별을 선택해 주세요.");
                return false;
            }
            if(!form_data.agree){
                alert("이용약관에 동의해 주세요.");
                return false;
            }

            // 중복 확인 후 사용 가능한 아이디인지 확인
            if($("#id_check_ok").hasClass('d-none')){
                alert("아이디 중복확인을 다시 해주세요.");
                return false;
            }
            //alert("회원가입 완료");

            // AJAX: 화면이동X, 응답값 JSON
            let url = $(this).attr("action");
            $.ajax({
                url : url
                ,type: 'POST'
                ,contentType: 'application/json' // 서버가 JSON 형식으로 데이터를 받도록 지정
                ,data: JSON.stringify(form_data) // JSON 문자열로 변환
                ,success : function (data){
                    if(data.code === 200){
                        alert("가입을 환영합니다! 로그인 해주세요.");
                        location.href = "/user/sign-in-view";
                    } else {
                        alert(data.error_message);
                    }
                }
                ,error: function (jqXHR, textStatus, errorThrown){
                    console.error("Request failed:", textStatus, errorThrown);
                    alert("서버 요청에 실패했습니다.");
                }
            })
        });
    })
</script>
</html>