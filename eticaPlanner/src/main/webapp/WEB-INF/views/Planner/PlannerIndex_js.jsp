<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${map_key}"></script>

<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", async function() {
        let value = "";
        try {
            const response = await fetch('/Planner/SelectPlanTitle');

            if (!response.ok) {
                throw new Error("데이터를 받아오지 못하고 있습니다.");
            }

            const datas = await response.json(); // JSON 형식으로 응답 데이터 파싱
            console.log(datas);

            value = datas.map((data)=>
            `<form action="/Planner/ModifyPlan" method="POST">
                <label > <p>\${data.tour_title}</p>
                    <input type="hidden" name="tour_title" value=\${data.tour_title}>
            	</label>
                    <button type="submit">Planner 수정</button>
            </form>
            <form action="/Planner/ViewPlan" method="POST">
                    <input type="hidden" name="tour_title" value=\${data.tour_title}>
                    <button type="submit">Planner 조회</button>
            </form>` ).join('');

            document.querySelector('.PlanSelect').innerHTML = value;


        } catch (error) {
            console.error("에러 발생:", error.message);
        }
    });
</script>