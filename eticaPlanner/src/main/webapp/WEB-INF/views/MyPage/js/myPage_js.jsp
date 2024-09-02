<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

document.addEventListener("DOMContentLoaded",() => {
    const hamburgerMenu = document.getElementById("hamburger-menu");
    const dropdownMenu = document.getElementById("dropdown-menu");

    hamburgerMenu.addEventListener("click", () => {
        document.body.classList.toggle("menu-open");
    });

        const myPageContentsLi = document.querySelectorAll(".mypagecontentsli");
        
        myPageContentsLi.forEach(li => {
            li.addEventListener("mouseover",() => {
                li.style.backgroundColor = "rgb(235, 235, 235)";
            });
            
            li.addEventListener("mouseout",() => {
                li.style.backgroundColor = "";
            });
            
        })
        
});



</script>