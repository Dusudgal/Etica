<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디 찾기 페이지</title>
    <link rel="stylesheet" href="<c:url value='/Resources/css/findIdPasswordStyle.css' />?v=1.1" type="text/css">



<%-- bootstrap --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>

</head>
<body>
    <form class="form-wrapper">
        <h2 class="mb-3">아이디 찾기</h2>
        <input type="email" name="email" placeholder="이메일을 입력하세요" required>
        <button type="button" id="findUserIdButton">아이디 찾기</button>
    </form>

<!-- 모달 구조 -->
<div class="modal fade" id="resultModal" tabindex="-1" role="dialog" aria-labelledby="resultModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title col-9" id="resultModalLabel">로그인 아이디 찾기</h5>
                <button type="button" class="close col-3" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="resultMessage"></div>
            <div class="modal-footer d-flex justify-content-end">
                <a href="/user/find-password-view" class="btn btn-secondary text-white mr-3" aria-hidden="true">비밀번호 찾기</a>
                <a href="/user/sign-in-view" class="btn btn-dark text-white" aria-hidden="true">로그인 하러가기</a>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        function findUserId() {
            let email = $("input[name=email]").val().trim();
            $.ajax({
                url: '/user/find-id',
                type: 'GET',
                data: { email: email },
                success: function(data) {
                    let resultMessage = $("#resultMessage");
                    if (data.code === 200) {
                        resultMessage.text("회원님의 아이디는 << " + data.user_id + " >> 입니다.");
                    } else {
                        resultMessage.text(data.error_message);
                    }
                    $('#resultModal').modal('show'); // 모달 표시
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error("Request failed:", textStatus, errorThrown);
                    alert("서버 요청에 실패했습니다.");
                }
            });
        }

        // 이메일 유효성 검사
        function validateEmail(email) {
            const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            return emailPattern.test(email);
        }

        $("#findUserIdButton").on('click', function() {
            let email = $("input[name=email]").val().trim();
            if (validateEmail(email)) {  // 이메일 유효성 검사
                findUserId();
            } else {
                alert("유효한 이메일 주소를 입력해주세요.");
            }
        });
    });
</script>
</body>
</html>