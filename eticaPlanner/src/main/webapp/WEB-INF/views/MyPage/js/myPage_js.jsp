<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
// 드롭다운
document.addEventListener("DOMContentLoaded", () => {
    // const hamburgerMenu = document.getElementById("hamburger-menu");
    // const dropdownMenu = document.querySelector(".main-container");
    
    // hamburgerMenu.addEventListener("click", () => {
    //     dropdownMenu.classList.toggle("menu-open");
    // });

    // 마이페이지 항목 호버 효과
    const myPageContentsLi = document.querySelectorAll(".mypage-item");
    myPageContentsLi.forEach(li => {
        li.addEventListener("mouseover", () => {
            li.style.backgroundColor = "rgb(235, 235, 235)";
        });

        li.addEventListener("mouseout", () => {
            li.style.backgroundColor = "";
        });
    });

    // 정보 섹션 표시 토글
    const informationSections = document.querySelectorAll(".information-section-unique");
    const lookInformation = document.querySelector("#look-information-section");
    const changePassword = document.querySelector("#change-password-section");
    const travelPlanner = document.querySelector("#travel-planner-section");

    travelPlanner.style.display = "block";

    document.querySelector(".mypage-contents").addEventListener("click", (event) => {
        const target = event.target.closest("li");

        informationSections.forEach(section => {
            section.style.display = "none";
        });

        if (target.textContent.includes("내 정보 보기")) {
            lookInformation.style.display = "block";
        } else if (target.textContent.includes("비밀번호 변경")) {
            changePassword.style.display = "block";
        } else if (target.textContent.includes("여행 플래너 보기")) {
            travelPlanner.style.display = "block";
        }
    });
});

</script>