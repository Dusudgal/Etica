<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/Resources/ReviewIndex.css'/>">
    <link rel="stylesheet" href="<c:url value='/Resources/css/signInStyle.css' />" type="text/css">
    <title>Review Main</title>
</head>
<body>
    <div class="bodies">
            <a href="/Review/ReviewMy" class="button">My Review</a> <!-- 나의 리뷰 -->
            <a href="/" class="button">Home</a> <!-- 홈으로 돌아가기 -->
    </div>
    <div class="search-box">
        <input type="text" class="touristSpotSearch" name="query" placeholder="관광지 검색" required /> <!-- 검색 입력 필드 -->
        <button type="button" class="touristSpotClick">검색</button> <!-- 검색 버튼 -->
    </div>

    <div class="search-results">
        <ul class="touristSpotListUl"></ul> <!-- 검색 결과를 표시할 리스트 -->
    </div>


    <script>
        const touristUl = document.querySelector('.touristSpotListUl');
        const tourSearch = document.querySelector('.touristSpotSearch');
        tourSearch.addEventListener('keyup' , (event) => {
                if(event.keyCode === 13){
                    findtouristSpot(event.target.value)
                }
            });
        document.querySelector('.touristSpotClick').addEventListener('click' , () => {
            findtouristSpot(tourSearch.value);
        });
        async function findtouristSpot(event){
               const encodedString = encodeURIComponent(event);
               const data = { 'keyword' : encodedString };
               try {
                   const response = await fetch( '/Planner/TourApiSearch' ,
                       {  method: 'POST' ,
                       headers : { 'Content-Type' : 'application/json' } ,
                       body: JSON.stringify({
                           keyword : encodedString
                       })
                   });
                   if (!response.ok) {
                       throw new Error("데이터를 받아오지 못하고있습니다.");
                   }
                   const jsonResponse = await response.json();
                   const jsondata = jsonResponse.response?.body?.items?.item || [];
                   console.log(jsonResponse);
                   // 데이터를 검색했는데 데이터가 있는 경우
                   if (Array.isArray(jsondata) && jsondata.length > 0) {
                       touristUl.innerHTML = ''; // 기존 목록을 비우기
                       let tourList = "";
                       for (let touristSpot of jsondata) {
                           tourList += `
                                <li>
                                    <h2>\${touristSpot.title}</h2>
                                    <img src="\${touristSpot.firstimage2}" alt="\${touristSpot.title}" onerror="this.style.display='none';" />
                                    <form action="/Review/ReviewGeneration" method="GET">
                                        <input type="hidden" name="title" value="\${touristSpot.title}" />
                                        <button type="submit" class="button">리뷰 생성</button>
                                    </form>
                                    <a href="/Review/ReviewPlus?title=\${touristSpot.title}" class="button">리뷰 더보기</a>
                                </li>`;

                       }
                       touristUl.innerHTML = tourList;
                   }else {
                       touristUl.innerHTML = '';
                       const newli = document.createElement('li');
                       touristUl.appendChild(newli);
                       const errmessage = document.createElement('H4');
                       errmessage.appendChild(document.createTextNode(`검색하신 \${event.target.value} 에 대한 정보가 없습니다.`));
                       newli.appendChild(errmessage);
                   }

                   tourSearch.value = ""; // 검색창 초기화
               }catch (error) {console.error('Error fetching data:', error);
           }
       }
    </script>
</body>
</html>
