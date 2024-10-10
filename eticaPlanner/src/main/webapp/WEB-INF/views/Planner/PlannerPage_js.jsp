<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${map_key}"></script>

<script type="text/javascript">
    document.querySelector('.touristSpotSearch').addEventListener('keyup' , (event) => {
        if(event.keyCode === 13){
            findtouristSpot(event)
        }
    });
    // 검색하는 관광지 Ul 태그
    const touristUl = document.querySelector('.touristSpotListUl');
    // 메모하기 위한 관광지 Ul 태그
    //메모 관광지 ul태그를 감싸는 div 태그
    const tourMemoListDiv = document.querySelector('.row:nth-child(2)');
    document.querySelector('.saveData').addEventListener('click' , SaveData);
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const tour_title = document.getElementById('TourTitle');
    let days = "";
    let tourList = null;
    let tourMapList = {};
    let memoMapList = {};
    let positions = [];
    let markers = [];
    // 카카오 map을 띄우는 곳
    let mapcontainer = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    let options = { //지도를 생성할 때 필요한 기본 옵션
	    center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
	    level: 3 //지도의 레벨(확대, 축소 정도)
    };

    let map = new kakao.maps.Map(mapcontainer, options); //지도 생성 및 객체 리턴

        // 관광지 검색 api 사용
        async function findtouristSpot(event){
            const encodedString = encodeURIComponent(event.target.value);
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

                    moveMap(jsondata[0].mapy , jsondata[0].mapx);

                    for (let touristSpot of jsondata) {
                        tourMapList[touristSpot.title] = {
                            mapx: touristSpot.mapx,
                            mapy: touristSpot.mapy
                        };
                        const newli = document.createElement('li');
                        createtag(newli , touristSpot.title , touristSpot.firstimage2 , `\${touristSpot.addr1} \${touristSpot.addr2}` );
                        //버튼 생성
                        const newBtn = document.createElement('input');
                        newBtn.type = "button";
                        newBtn.className = "img-button";
                        newli.appendChild(newBtn);
                        newBtn.addEventListener('click', tourBtnClick);
                        touristUl.appendChild(newli);
                    }
                }else {
                    touristUl.innerHTML = '';
                    const newli = document.createElement('li');
                    touristUl.appendChild(newli);
                    const errmessage = document.createElement('H4');
                    errmessage.appendChild(document.createTextNode(`검색하신 \${event.target.value} 에 대한 정보가 없습니다.`));
                    newli.appendChild(errmessage);
                }

                event.target.value = ""; // 검색창 초기화
            }catch (error) {console.error('Error fetching data:', error);
        }
    }


    function tourBtnClick(e){
        //버튼 위에 부모인 li태그에서 자식의 정보를 불러오기 위한 tourli
        const tourli = e.target.closest('li');

        const tourData = {
            imgSrc : null,
            titleText : null,
            address : null
        };

        if(!tourList){
            return alert("날짜를 입력해주세요");
        }
        // 버튼에 함께 있는 태그들의 데이터 담기
        Array.from(tourli.children).forEach(child =>{
            if(child.tagName === 'IMG') tourData.imgSrc = child.src;
            else if(child.tagName === 'H4') tourData.titleText = child.textContent.trim();
            else if(child.tagName === 'P') tourData.address = child.textContent.trim();
        });
        const li = document.createElement('li');
        createtag(li , tourData.titleText , tourData.imgSrc , tourData.address );

        const tourMaptitle = tourMapList[tourData.titleText];

        moveMap(tourMaptitle.mapy , tourMaptitle.mapx);

        if (!memoMapList[tourList.getAttribute('data-date')]) {
            memoMapList[tourList.getAttribute('data-date')] = {}; // 빈 객체로 초기화
        }

        memoMapList[tourList.getAttribute('data-date')][tourData.titleText] = {
                mapx: tourMaptitle.mapx ,
                mapy: tourMaptitle.mapy
        };
        const input = document.createElement('input');
        input.type = 'text';
        input.placeholder = "메모장 입니다."
        li.appendChild(input);

        const button = document.createElement('input');
        button.type = "button";
        button.value = "삭제";
        button.addEventListener('click', deleteTourMemo);
        li.appendChild(button);

        tourList.appendChild(li);

        setMarker(tourMaptitle , tourData);

    }

    function deleteTourMemo(e){
        const tourli = e.target.closest('li');
        tourli.parentElement.removeChild(tourli);
        removeMarker(tourli.querySelector('h4').textContent);

    }
    function createtag( li , h4Text , imgsrc , pText ){
        const title = document.createElement('H4');
        const img = document.createElement('img');
        const newP = document.createElement('p');
        newP.textContent = pText;
        title.textContent = h4Text;
        img.src = imgsrc;
        img.onerror = () => {img.style.display = 'none';};
        li.appendChild(img);
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
            const date = ul.getAttribute('data-date');

            for( const li of tourli ){
                const title = li.getElementsByTagName('h4')[0]?.textContent;
                const memoData = memoMapList[date] ? memoMapList[date][title] : null;
                tourMemodata.push({
                    date : date || null,
                    imgSrc : li.getElementsByTagName('img')[0].src || null,
                    addr : li.getElementsByTagName('p')[0].textContent || null,
                    title : title || null ,
                    inputValue : li.getElementsByTagName('input')[0].value || null ,
                    mapx : memoData ? memoData.mapx : null ,
                    mapy : memoData ? memoData.mapy : null
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
        SavePlanData(data);
    }
    async function SavePlanData(data){
        if(tour_title.value === ""){
            return alert("제목을 입력해주세요");
        }
        if(data.tourMemoData.length > 0 && data.tourTitleData){
            try{
                const response = await fetch('/Planner/PlannerSaveData' , {
                    method : 'POST',
                    headers : { 'content-Type' : 'application/json' },
                    body : JSON.stringify(data)
                });

                if (response.ok) {
                    const result = await response.text();
                    if (result === "login_fail") {
                        if(confirm("현재 로그인되어있지 않습니다. ") == true){
                            window.location.href = '/user/sign-in-view';
                        }else{
                            return false;
                        }
                    } else if(result === "success") {
                        // 성공 시 페이지 리디렉션
                        window.location.href = '/Planner/PlannerSaveSuccess';
                    }else {
                        alert("서버에 저장이 실패하셨습니다. ");
                    }
                } else {
                    alert("서버에 저장이 실패하셨습니다. ");
                }
            }catch(error){
                console.error('요청실패' , error);
            }
        }else{
            return console.log("저장할 데이터가 없습니다.");
        }
    }
    function moveMap( mapx , mapy ){
         let moveLatLon = new kakao.maps.LatLng(mapx, mapy);
         map.setCenter(moveLatLon);
    }
    function setMarker(tourMaptitle , tourData){
        let markerPosition  = new kakao.maps.LatLng( tourMaptitle.mapy , tourMaptitle.mapx);  // 마커가 표시될 위치
        let marker = new kakao.maps.Marker({  // 마커를 생성한다
            position: markerPosition ,
            ul : tourList.getAttribute('data-date')
        });

        marker.setMap(map);
        markers.push({ marker : marker , title : tourData.titleText , ul : tourList.getAttribute('data-date') });
    }

    function setMarkersView(date , map){
        markers.forEach(markerdata => {
            if(markerdata.ul === date){
                markerdata.marker.setMap(map);
            }
        });
    }

    function removeMarker(Text) {
        // markers 배열에서 h4Text와 일치하는 마커 찾기
        const index = markers.findIndex(markerObj => markerObj.title === Text);

        if (index !== -1) {
            // 마커 삭제
            markers[index].marker.setMap(null); // 맵에서 마커 제거

            // 배열에서 해당 마커 객체 제거
            markers.splice(index, 1);
        }
    }

    // 여행 일자를 클릭시에 버튼을 생성해서 일자별로 볼수있게 만들어주는 함수
    document.addEventListener("DOMContentLoaded", function() {
        const durationSpan = document.getElementById('duration');
        const dayButtonsContainer = document.getElementById('dayButtons');
        const tourMemoContainer = document.querySelector('.row:nth-child(2) > .tourMemo');

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
            if(tourList){
                setMarkersView(tourList.getAttribute('data-date') , null);
            }
            const allLists = document.querySelectorAll('.row:nth-child(2) .touristSpotMemo');
            allLists.forEach(ul => {
                if (parseInt(ul.getAttribute('data-date')) === day) {
                    ul.style.display = 'block'; // 해당 날짜의 ul 태그 보이기
                    tourList = ul;
                } else {
                    ul.style.display = 'none'; // 다른 날짜의 ul 태그 숨기기
                }
            });
            if(tourList){
                setMarkersView(tourList.getAttribute('data-date') , map);
            }
        }

        startDateInput.addEventListener('change', updateDuration);
        endDateInput.addEventListener('change', updateDuration);

        const touristSpotClick = document.querySelector('.touristSpotClick');
        if (touristSpotClick) {
            touristSpotClick.addEventListener('click', findtouristSpot);
        }

        const saveDataButton = document.querySelector('.saveData');
        if (saveDataButton) {
            saveDataButton.addEventListener('click', SaveData);
        }
        });

</script>