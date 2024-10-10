<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script type="text/javascript">
        function startCountdown(lockTime) {
            var interval = setInterval(function() {
                var minutes = Math.floor(lockTime / 60);
                var seconds = lockTime % 60;

                document.getElementById("remaining-time").innerText = "남은 시간: " + minutes + "분 " + seconds + "초";

                if (lockTime <= 0) {
                    clearInterval(interval);
                    document.getElementById("retry-link").style.display = "block";
                    document.getElementById("remaining-time").style.display = "none";
                }

                lockTime--;
            }, 1000);
        }
    </script>