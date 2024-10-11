<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
// 드롭다운


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
    const lookInformation = document.querySelector("#look-information");
    const changePassword = document.querySelector("#change-password");
    const travelPlanner = document.querySelector("#travel-planner");
    const memberContainer = document.querySelector("#member-container");

    lookInformation.style.display = "block"

    document.querySelector(".mypagecontents").addEventListener("click", (event) => {
        const target = event.target.closest("li")

    informationsection.forEach(section => {
        section.style.display = "none";
    })
        
        informationsection.forEach(list => {
            if(target.textContent.includes("내 정보")) {
                lookInformation.style.display = "block";
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
/*document.addEventListener("DOMContentLoaded", () => {
    const phonenumberConfirm = document.querySelector(".phonenumber-confirm");

    const phonenumberForm = document.getElementById("phonenumber-form")
*/
    // passwordConfirm.addEventListener("mousedown", () => {
    //     passwordform.textContent = "${user.userPassword}";
    // });
    // passwordConfirm.addEventListener("mouseup", () => {
    //     passwordform.textContent = "******";
    // });
    // passwordConfirm.addEventListener("mouseleave", () => {
    //     passwordform.textContent = "******";
    // });
/*    if (phonenumberConfirm && phonenumberForm) {  // 요소가 존재하는지 확인
        phonenumberConfirm.addEventListener("mousedown", () => {
            phonenumberForm.textContent = "${user.userPhone}" // textContent를 통해 HTML 텍스트를 변경
        });
        phonenumberConfirm.addEventListener("mouseup", () => {
            phonenumberForm.textContent = "010-****-****"; // textContent를 통해 HTML 텍스트를 변경
        });
    } else {
        console.error("Required elements are not found in the DOM.");
    }
})*/

</script>