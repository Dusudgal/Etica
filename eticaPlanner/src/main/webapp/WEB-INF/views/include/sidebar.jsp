<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="main-container">
    <div class="menu-container">
      <div class="hamburger-menu" id="hamburger-menu">
        <span></span>
        <span></span>
        <span></span>
      </div>
    </div>
    <nav id="dropdown-menu">
      <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/Planner/PlannerPage">Planner</a></li>
        <li><a href="/Review/ReviewIndex">Review</a></li>
      </ul>
    </nav>
  </div>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const hamburgerMenu = document.getElementById("hamburger-menu");
    const dropdownMenu = document.querySelector(".main-container");
    
    hamburgerMenu.addEventListener("click", () => {
        dropdownMenu.classList.toggle("menu-open");
    });
    });
</script>
