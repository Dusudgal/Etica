<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="look-information-section" class="information-section-unique">
    <div class="middle-container">
      <table class="middle-table">
        <tr>
          <th>아이디</th>
          <td>${user.userId}</td>
        </tr>
        <tr>
          <th>닉네임</th>
          <td>${user.userNickname}</td>
        </tr>
        <tr>
          <th>성함</th>
          <td>${user.userName}</td>
        </tr>
        <tr>
          <th>생년월일</th>
          <td>${user.userBirth}</td>
        </tr>
      </table>
    </div>
</div>