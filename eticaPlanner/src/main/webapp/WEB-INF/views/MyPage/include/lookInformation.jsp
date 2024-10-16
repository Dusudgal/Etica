<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="look-information" class="information-section">
<c:set var="sessionInfo" value="${sessionScope.sessionInfo}" />
    <div class="middle-container">
        <table class="middle-table">
            <c:if test="${not empty sessionInfo.user_id}">
                <tr>
                    <th>아이디 : </th>
                    <td>  ${sessionInfo.user_id}</td>
                </tr>
                <tr>
                    <th>핸드폰 : </th>
                    <td>  ${user.user_phone}</td>
                </tr>
                <tr>
                    <th>성함 : </th>
                    <td>  ${user.user_name}</td>
                </tr>
                <tr>
                    <th>생년월일 : </th>
                    <td>  ${user.user_birth}</td>
                </tr>
            </c:if>
            <c:if test="${not empty sessionInfo.kakao_id}">
                <tr>
                    <th>아이디 : </th>
                    <td>  카카오 로그인 유저입니다.</td>
                </tr>
                <tr>
                    <th>이메일 : </th>
                    <td>  ${user.user_email}</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>