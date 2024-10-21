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
        <div class="searchPageing">
            <span class="pageNumbers"></span>
        </div>
    </div>


    <script>
        let currentPage = 0;
        let totalPages = 0;
        let currentPageGroup = 1; // 현재 페이지 그룹
        const pagesPerGroup = 5; // 한 그룹에 표시할 페이지 수
        const touristUl = document.querySelector('.touristSpotListUl');
        const tourSearch = document.querySelector('.touristSpotSearch');
        tourSearch.addEventListener('keyup' , (event) => {
                if(event.keyCode === 13){
                    currentPage = 1; // 페이지 초기화
                    keyword = event.target.value;
                    findtouristSpot(keyword  , currentPage)
                    event.target.value = ""; // 검색창 초기화
                }
            });
        document.querySelector('.touristSpotClick').addEventListener('click' , () => {
            currentPage = 1; // 페이지 초기화
            keyword = tourSearch.value;
            findtouristSpot(keyword  , currentPage)
            tourSearch.value = ""; // 검색창 초기화
        });
        async function findtouristSpot(event){
               const encodedKeyword = encodeURIComponent(keyword);
               const page = currentPage;
               try {
                   const response = await fetch(`/Planner/TourApiSearch?keyword=\${encodedKeyword}&page=\${page}`);
                   if (!response.ok) {
                       throw new Error("데이터를 받아오지 못하고있습니다.");
                   }
                   const jsonResponse = await response.json();
                   const jsondata = jsonResponse.response?.body?.items?.item || [];

                   totalPages = Math.ceil(jsonResponse.response.body.totalCount / 30);

                   // 데이터를 검색했는데 데이터가 있는 경우
                   if (Array.isArray(jsondata) && jsondata.length > 0) {
                       touristUl.innerHTML = ''; // 기존 목록을 비우기
                       let tourList = "";
                       for (let touristSpot of jsondata) {
                           tourList += `
                                <li>
                                    <h2>\${touristSpot.title}</h2>
                                    <img src="\${touristSpot.firstimage}" alt="\${touristSpot.title}" onerror="this.style.display='none';" />
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
                       errmessage.appendChild(document.createTextNode(`검색하신 \${keyword} 에 대한 정보가 없습니다.`));
                       newli.appendChild(errmessage);
                   }

                   updatePagination(); // 페이지 네이션
               }catch (error) {console.error('Error fetching data:', error);
           }
       }

        function updatePagination() {
            const pageNumbersContainer = document.querySelector('.pageNumbers');
            pageNumbersContainer.innerHTML = ''; // 기존 페이지 번호 초기화

            // 총 페이지 수 계산
            const totalPageGroups = Math.ceil(totalPages / pagesPerGroup); // 페이지 그룹의 총 수
            const startPage = (currentPageGroup - 1) * pagesPerGroup + 1; // 현재 그룹의 시작 페이지
            const endPage = Math.min(startPage + pagesPerGroup - 1, totalPages); // 현재 그룹의 끝 페이지

            // 페이지 버튼 생성
            for (let i = startPage; i <= endPage; i++) {
                const pageButton = document.createElement('button');
                pageButton.textContent = i;
                pageButton.className = 'pageButton';
                pageButton.onclick = () => {
                    currentPage = i; // 클릭한 페이지로 변경
                    findtouristSpot(keyword, currentPage); // 데이터 요청
                };
                pageNumbersContainer.appendChild(pageButton);
            }

            // 이전 그룹 버튼
            if (currentPageGroup > 1) {
                const prevGroupButton = document.createElement('button');
                prevGroupButton.textContent = '이전';
                prevGroupButton.onclick = () => {
                    currentPageGroup--;
                    updatePagination(); // 페이지 번호 업데이트
                };
                pageNumbersContainer.prepend(prevGroupButton);
            }

            // 다음 그룹 버튼
            if (currentPageGroup < totalPageGroups) {
                const nextGroupButton = document.createElement('button');
                nextGroupButton.textContent = '다음';
                nextGroupButton.onclick = () => {
                    currentPageGroup++;
                    updatePagination(); // 페이지 번호 업데이트
                };
                pageNumbersContainer.appendChild(nextGroupButton);
            }
        }
    </script>
</body>
</html>
