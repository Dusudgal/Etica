<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../APIkeys/Sc_key.jsp"/>

<script type="text/javascript">
    document.querySelector('.touristSpotClick').addEventListener('click' , but);
    // 검색하는 관광지 Ul 태그
    const touristUl = document.querySelector('.touristSpotListUl');
    // 메모하기 위한 관광지 Ul 태그
    const tourMemoUl = document.querySelector('.touristSpotMemo');
    //메모 관광지 ul태그를 감싸는 div 태그
    const TourMemoListDiv = document.querySelector('.row:nth-child(2)');
    const app_key = Api_keys.Tour_api_key;

        // 관광지 검색 api 사용
        async function but(){
            const searchString = document.querySelector('.touristSpotSearch');
            const codeString = searchString.value;
            const PageNumber = 1;
            const numOfRows = 30;
            api_key_select;
            SaveData();
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
                            touristUl.innerHTML = ''; // 기존 목록을 비우기
                            
                            //
                            for (let touristSpot of jsondata) {
                                const newli = document.createElement('li');
                                createtag(newli , `\${touristSpot.addr1} \${touristSpot.addr2}` , touristSpot.firstimage2 , touristSpot.title );
                                //버튼 생성
                                const newBtn = document.createElement('input');
                                newBtn.type = "button";
                                newBtn.class = "img-button";
                                newBtn.value = "추가";
                                newli.appendChild(newBtn);
                                newBtn.addEventListener('click', tourBtnClick);
                                touristUl.appendChild(newli);
                            }
                        }else {
                            touristUl.innerHTML = '';
                            const newli = document.createElement('li');
                            touristUl.appendChild(newli);
                            const errmessage = document.createElement('H4');
                            errmessage.appendChild(document.createTextNode(`검색하신 '\${codeString}'에 대한 정보가 없습니다.`));
                            newli.appendChild(errmessage);
                        }
                        
                        searchString.value = ""; // 검색창 초기화
                        
                    } catch (error) {console.error('Error fetching data:', error);
                }
            }
            
    function tourBtnClick(e){
        //버튼 위에 부모인 li태그 밑에 있는 정보를 불러오기 위한 tourli
        const tourli = e.target.closest('li');
        const tourData = {
            imgSrc : null,
            titleText : null,
            address : null
        };
        // 버튼에 함께 있는 태그들의 데이터 담기
        Array.from(tourli.children).forEach(child =>{
            if(child.tagName === 'IMG') tourData.imgSrc = child.src;
            else if(child.tagName === 'H4') tourData.titleText = child.textContent.trim();
            else if(child.tagName === 'P') tourData.address = child.textContent.trim();
        });
        const newli = document.createElement('li');
        createtag(newli , tourData.titleText , tourData.imgSrc , tourData.address );
        const newinputText = document.createElement('input');
        newinputText.type = 'text';
        newli.appendChild(newinputText);
        tourMemoUl.appendChild(newli);
    }

    function createtag( li , h4Text , imgsrc , pText ){
        const title = document.createElement('H4');
        const newimg = document.createElement('img');
        const newP = document.createElement('p');
        newP.textContent = pText;
        title.textContent = h4Text;
        newimg.src = imgsrc;
        li.appendChild(newimg);
        li.appendChild(title);
        li.appendChild(newP);
    }

    function api_key_select(api_key_name){
        const api_key = api_key_name === 'Tourkey' ? 'Tourkey' : "null";
        console.log(api_key);
        const feurl = '/Apikey/GetTourkey';
            fetch(feurl)
            .then((response) => response.json())
            .then((result) => console.log(result));
    }

    function SaveData(){
        const TourMemodata = [];
        const TourMemoListUl = TourMemoListDiv.getElementsByTagName('ul');

        //모든 ul 요소 순회
        for(const ul of TourMemoListUl){

            // ul 태그 안에 있는 li 요소 
            const Tourli = ul.getElementsByTagName('li');

            for( const li of Tourli ){
                TourMemodata.push({
                    date : ul.getAttribute('data-date') || null,
                    imgSrc : li.getElementsByTagName('img')[0].src || null,
                    pText : li.getElementsByTagName('p')[0].textContent || null,
                    h4Text : li.getElementsByTagName('h4')[0].textContent || null,
                    inputVlaue : li.getElementsByTagName('input')[0].value || null,
                });
            }
        }
        if(TourMemodata){
            fetch('/Planner/PlannerSaveData' , {
                method : 'POST',
                headers : {
                    'content-Type' : 'aplication/json'
                },
                body : JSON.stringify(TourMemodata)
            })
            .then(response => response.json())
            .then(result => {
                console.log("성공 : " , result);
            })
            .catch(error => {
                console.error('에러 :', error);
            });
        }else{
            console.log("데이터가 없습니다.");
        }
    }

</script>
