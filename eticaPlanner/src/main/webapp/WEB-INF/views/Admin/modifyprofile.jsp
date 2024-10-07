<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/Resources/css/Admin/modify_profile.css'/>" type="text/css">
    <title>관리자정보수정페이지</title>
</head>
<body>
    <jsp:include page="login_ok.jsp"/>
    <div class=head>
    <div class=container>
        <h2>비밀번호 수정</h2>
        <form method="post" action="<c:url value='/Admin/modifyprofile'/>" id="modifyform">
            <div>
                <label for="admin_id">아이디</label>
                <input type="text" id="admin_id" name="admin_id" value="${admin.adminId}" readonly />
            </div>
             <div>
                <label for="admin_name">이름</label>
                <input type="text" id="admin_name" name="admin_name" value="${admin.adminName}">
            </div>
            <div>
                 <label for="admin_phone">전화번호</label>
                 <input type="text" id="admin_phone" name="admin_phone" value="${admin.adminPhone}">
            </div>
            <div>
                <label for="admin_email">이메일</label>
                <input type="email" id="admin_email" name="admin_email" value="${admin.adminEmail}">
            </div>
            <div>
                <label for="password">새 비밀번호</label>
                <input type="password" id="admin_password" name="admin_password">
            </div>
            <div>
                <label for="password">새 비밀번호 확인</label>
                <input type="password" id="admin_password_again" name="admin_password_again">
            </div>
            <button type="submit">수정하기</button>
        </form>
    </div>
    </div>
</body>
</html>