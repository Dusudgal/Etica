<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
// 드롭다운
document.addEventListener("DOMContentLoaded", () => {
    const hamburgerMenu = document.getElementById("hamburger-menu");
    const dropdownMenu = document.querySelector(".main-container");
    
    hamburgerMenu.addEventListener("click", () => {
        // dropdownMenu.classList.toggle("show"); // 올바른 드롭다운 메뉴를 대상으로 하는지 확인
        dropdownMenu.classList.toggle("menu-open");
    });
});


document.addEventListener("DOMContentLoaded", () => {

    const myPageContentsLi = document.querySelectorAll(".mypagecontentsli");
    
    myPageContentsLi.forEach(li => {
        li.addEventListener("mouseover",() => {
            li.style.backgroundColor = "rgb(235, 235, 235)";
        });
        
        li.addEventListener("mouseout",() => {
            li.style.backgroundColor = "";
        });
        
    })
})
document.addEventListener("DOMContentLoaded", () => {
    const informationsection = document.querySelectorAll(".information-section");
    const changeInformation = document.querySelector("#change-information");
    const changePassword = document.querySelector("#change-password");
    const travelPlanner = document.querySelector("#travel-planner");
    const memberContainer = document.querySelector("#member-container");

    changeInformation.style.display = "block"

    document.querySelector(".mypagecontents").addEventListener("click", (event) => {
        const target = event.target.closest("li")

    informationsection.forEach(section => {
        section.style.display = "none";
    })
        
        informationsection.forEach(list => {
            if(target.textContent.includes("내 정보 수정")) {
                changeInformation.style.display = "block";
            } else if (target.textContent.includes("비밀번호 변경")) {
                changePassword.style.display = "block";
            } else if (target.textContent.includes("여행 플래너 보기")) {
                travelPlanner.style.display = "block";
            } else if (target.textContent.includes("임시")){ 
                memberContainer.style.display = "block";
            }
        })
    })

})


</script>