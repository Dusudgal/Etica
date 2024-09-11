<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="./APIkeys/Sc_key.jsp"/>

<script type="text/javascript">
    document.querySelector('.touristSpotClick').addEventListener('click' , but);
    // 검색하는 관광지 Ul 태그
    const touristUl = document.querySelector('.touristSpotListUl');
    // 메모하기 위한 관광지 Ul 태그
    //메모 관광지 ul태그를 감싸는 div 태그
    const tourMemoListDiv = document.querySelector('.row:nth-child(2)');
    const app_key = Api_keys.Tour_api_key;
    document.querySelector('.saveData').addEventListener('click' , SaveData);
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const tour_title = document.getElementById('TourTitle');
    let days = "";
    let tourList = "";

        // 관광지 검색 api 사용
        async function but(){
            const searchString = document.querySelector('.touristSpotSearch');
            const codeString = searchString.value;
            const PageNumber = 1;
            const numOfRows = 30;
            const encodedString = encodeURIComponent(codeString);
            const url = `https://apis.data.go.kr/B551011/KorService1/searchKeyword1?numOfRows=\${numOfRows}&pageNo=\${PageNumber}&MobileOS=ETC&MobileApp=1&_type=json&listYN=Y&arrange=A&keyword=\${encodedString}&contentTypeId=12&serviceKey=\${app_key} `;
            try {
                const response = await fetch( url ,
                {  method: 'GET' });
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                const jsonResponse = await response.json();
                const jsondata = jsonResponse.response?.body?.items?.item || [];

                // 데이터를 검색했는데 데이터가 있는 경우
                if (Array.isArray(jsondata) && jsondata.length > 0) {
                    touristUl.innerHTML = ''; // 기존 목록을 비우기

                    for (let touristSpot of jsondata) {
                        const newli = document.createElement('li');
                        createtag(newli , touristSpot.title , touristSpot.firstimage2 , `\${touristSpot.addr1} \${touristSpot.addr2}` );
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
            }catch (error) {console.error('Error fetching data:', error);
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
        if(tour_title.value === ""){
            return alert("제목을 입력해주세요");
        }
        if(!tourList){
            return alert("날짜를 입력해주세요");
        }
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
        tourList.appendChild(newli);
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

    function SaveData(){
        const tourMemodata = [];
        const tourMemoListUl = tourMemoListDiv.getElementsByTagName('ul');

        //모든 ul 요소 순회
        for(const ul of tourMemoListUl){

            // ul 태그 안에 있는 li 요소
            const tourli = ul.getElementsByTagName('li');

            for( const li of tourli ){
                tourMemodata.push({
                    date : ul.getAttribute('data-date') || null,
                    imgSrc : li.getElementsByTagName('img')[0].src || null,
                    addr : li.getElementsByTagName('p')[0].textContent || null,
                    title : li.getElementsByTagName('h4')[0].textContent || null,
                    inputValue : li.getElementsByTagName('input')[0].value || null,
                });
            }
        }
        const tourTitleData = {
            tour_title : tour_title.value ,
            startDate : startDateInput.value,
            endDate : endDateInput.value
        };
        const data = {
            tourTitleData : tourTitleData,
            tourMemoData : tourMemodata
        }
        console.log(tourTitleData);
        console.log(tourMemodata);
        SaveSebData(data);
    }
    async function SaveSebData(data){
        console.log(data);
        if(data.tourMemoData.length > 0 && data.tourTitleData){
            try{
                const response = await fetch('/Planner/PlannerSaveData' , {
                    method : 'POST',
                    headers : {
                        'content-Type' : 'application/json'
                    },
                    body : JSON.stringify(data)
                });
                const resData = await response.json();
                if(!response.ok){
                    console.error('요청실패' , resData);
                    throw new Error(`오류 \${response.status}: \${resData.message || '알 수 없는 오류'}`);
                }
            }catch(error){
                console.error('요청실패' , error);
            }
        }else{
            return console.log("저장할 데이터가 없습니다.");
        }
    }
    document.addEventListener("DOMContentLoaded", function() {
        const durationSpan = document.getElementById('duration');
        const dayButtonsContainer = document.getElementById('dayButtons');
        const tourMemoContainer = document.querySelector('.row:nth-child(2)');

        function updateDuration() {
            const startDateValue = startDateInput.value;
            const endDateValue = endDateInput.value;

            if(startDateValue && endDateValue) {
                const startDate = new Date(startDateValue);
                const endDate = new Date(endDateValue);

                // 하루를 포함한 여행 기간 계산
                const timeDifference = endDate - startDate;
                const dayDifference = Math.ceil(timeDifference / (1000 * 60 * 60 * 24)) + 1;

                if (durationSpan) durationSpan.textContent = dayDifference;

                if (dayButtonsContainer) updateDayButtons(dayDifference);
                if (tourMemoContainer) updateTourMemoList(dayDifference);
                // 기본적으로 첫째 날을 보여줍니다.
                if (dayDifference > 0) {
                    showDayList(1); // 첫째 날 표시
                }
            } else {
                if (durationSpan) durationSpan.textContent = '0';
                if (dayButtonsContainer) clearDayButtons();
                if (tourMemoContainer) clearTourMemoLists();
            }
        }

    function updateDayButtons(days) {
        if (dayButtonsContainer) {
            dayButtonsContainer.innerHTML = ''; // 기존 버튼 제거

            for (let i = 1; i <= days; i++) {
                const button = document.createElement('button');
                button.textContent = `\${i}일차`;
                button.addEventListener('click', () => showDayList(i));
                dayButtonsContainer.appendChild(button);
            }
        }
    }

    function updateTourMemoList(days) {
        if (tourMemoContainer) {
            tourMemoContainer.innerHTML = ''; // 기존 ul 태그 제거

            for (let i = 1; i <= days; i++) {
                const newUl = document.createElement('ul');
                newUl.classList.add('touristSpotMemo');
                newUl.setAttribute('data-date', i); // 각 ul에 날짜 설정
                newUl.style.display = 'none'; // 초기에는 숨김
                tourMemoContainer.appendChild(newUl);
            }
        }
    }

    function clearDayButtons() {
        if (dayButtonsContainer) {
            dayButtonsContainer.innerHTML = '';
        }
    }

    function clearTourMemoLists() {
        if (tourMemoContainer) {
            tourMemoContainer.innerHTML = '';
        }
    }

    function showDayList(day) {
        const allLists = document.querySelectorAll('.row:nth-child(2) .touristSpotMemo');
        allLists.forEach(ul => {
            if (parseInt(ul.getAttribute('data-date')) === day) {
                ul.style.display = 'block'; // 해당 날짜의 ul 태그 보이기
                tourList = ul;
            } else {
                ul.style.display = 'none'; // 다른 날짜의 ul 태그 숨기기
            }
        });
    }

    startDateInput.addEventListener('change', updateDuration);
    endDateInput.addEventListener('change', updateDuration);

    const touristSpotClick = document.querySelector('.touristSpotClick');
    if (touristSpotClick) {
        touristSpotClick.addEventListener('click', but);
    }

    const saveDataButton = document.querySelector('.saveData');
    if (saveDataButton) {
        saveDataButton.addEventListener('click', SaveData);
    }
});

</script>