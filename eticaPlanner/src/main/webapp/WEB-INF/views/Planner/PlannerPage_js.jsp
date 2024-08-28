<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../APIkeys/Sc_key.jsp"/>

<script type="text/javascript">
    document.querySelector('.touristSpotClick').addEventListener('click' , but);
        const touristUl = document.querySelector('.touristSpotListUl');
        async function but(){
            const app_key = koreapublic_api_key.public_api_key;
            const searchString = document.querySelector('.touristSpotSearch');
            console.log(app_key);
            const codeString = searchString.value;
            const PageNumber = 1;
            const numOfRows = 30;
            const encodedString = encodeURIComponent(codeString);
            const url = `https://apis.data.go.kr/B551011/KorService1/searchKeyword1?numOfRows=\${numOfRows}&pageNo=\${PageNumber}&MobileOS=ETC&MobileApp=1&_type=json&listYN=Y&arrange=A&keyword=\${encodedString}&contentTypeId=12&serviceKey=\${app_key} `;
            try {
                const response = await fetch( url ,
                { method : 'GET' , });
                if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }

                        const jsonResponse = await response.json();
                        console.log(jsonResponse);

                        // JSON 데이터 경로 확인
                        const jsondata = jsonResponse.response?.body?.items?.item || [];

                        // 데이터를 검색했는데 데이터가 있는 경우
                        if (Array.isArray(jsondata) && jsondata.length > 0) {
                            const touristUl = document.querySelector('.touristSpotListUl');
                            touristUl.innerHTML = ''; // 기존 목록을 비우기

                            for (let touristSpot of jsondata) {
                                const title = document.createElement('H5');
                                const newli = document.createElement('li');
                                const newimg2 = document.createElement('img');
                                const addr = document.createTextNode(`${touristSpot.addr1} ${touristSpot.addr2}`);
                                newimg2.src = touristSpot.firstimage2;
                                title.appendChild(document.createTextNode(touristSpot.title));
                                newli.appendChild(newimg2);
                                newli.appendChild(title);
                                newli.appendChild(addr);
                                touristUl.appendChild(newli);
                            }
                        } else {
                            console.log('No data found');
                            // 데이터가 없을 때 처리할 코드 추가
                        }

                        searchString.value = ""; // 검색창 초기화

                    } catch (error) {
                        console.error('Error fetching data:', error);
                    }
                }
            </script>
