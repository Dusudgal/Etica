document.addEventListener("DOMContentLoaded", function() {
    const userName = "관리자";
    const loginMessage = `${userName}님이 로그인했습니다.`;
    document.getElementById("loginState").textContent = loginMessage;