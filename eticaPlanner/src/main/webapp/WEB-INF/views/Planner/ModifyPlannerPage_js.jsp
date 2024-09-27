<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${map_key}"></script>

<script type="text/javascript">
    document.querySelector('.touristSpotClick').addEventListener('click' , but);
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
    let tourList = "";


        // 관광지 검색 api 사용
        async function but(){
            const searchString = document.querySelector('.touristSpotSearch');
            const codeString = searchString.value;
            const encodedString = encodeURIComponent(codeString);
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
                console.log(jsonResponse.response.body.items);
                console.log(jsondata);

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

        const input = document.createElement('input');
        input.type = 'text';
        li.appendChild(input);

        const button = document.createElement('input');
        button.type = "button";
        button.value = "삭제";
        button.addEventListener('click', deleteTourMemo);
        li.appendChild(button);

        tourList.appendChild(li);
    }

    function deleteTourMemo(e){
        const tourli = e.target.closest('li');
        tourli.parentElement.removeChild(tourli);

    }
    function createtag( li , h4Text , imgsrc , pText ){
        const title = document.createElement('H4');
        const img = document.createElement('img');
        const newP = document.createElement('p');
        newP.textContent = pText;
        title.textContent = h4Text;
        img.src = imgsrc;
        img.onerror = () => newimg.style.display='none';
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
            planNo : document.getElementById('planNo').value ,
            tour_title : tour_title.value ,
            startDate : startDateInput.value,
            endDate : endDateInput.value
        };
        const data = {
            tourTitleData : tourTitleData,
            tourMemoData : tourMemodata
        }
        ModifyPlanData(data);
    }
    async function ModifyPlanData(data){
        if(tour_title.value === ""){
            return alert("제목을 입력해주세요");
        }
        if(data.tourMemoData.length > 0 && data.tourTitleData){
            try{
                const response = await fetch('/Planner/ModifyPlanData' , {
                    method : 'POST',
                    headers : { 'content-Type' : 'application/json' },
                    body : JSON.stringify(data)
                });

                if (response.ok) {
                    const result = await response.text();
                    if (result === "login_fail") {
                        window.location.href = '/user/sign-in-view';
                    } else if(result === "success") {
                        // 성공 시 페이지 리디렉션
                        window.location.href = '/Planner/PlannerSaveSuccess';
                    }else {
                        // 실패 시 페이지 리디렉션
                        alert("서버에 저장이 실패하셨습니다.");
                    }
                } else {
                // 서버 오류 시 페이지 리디렉션
                    window.location.href = '/Planner/PlannerSavefail';
                }
            }catch(error){
                console.error('요청실패' , error);
            }
        }else{
            return console.log("저장할 데이터가 없습니다.");
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

                if (startDateValue && endDateValue) {
                    const startDate = new Date(startDateValue);
                    const endDate = new Date(endDateValue);
                    const timeDifference = endDate - startDate;
                    const dayDifference = Math.ceil(timeDifference / (1000 * 60 * 60 * 24)) + 1;

                    if (durationSpan) durationSpan.textContent = dayDifference;

                    if (dayButtonsContainer) updateDayButtons(dayDifference);
                    if (tourMemoContainer) updateTourMemoList(dayDifference);
                    if (dayDifference > 0) {
                        showDayList(1); // 첫째 날 표시
                    }
                } else {
                    if (durationSpan) durationSpan.textContent = '0';
                    clearDayButtons();
                    clearTourMemoLists();
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

                        // 해당 날짜의 메모 데이터를 가져옴
                        if(planmemo){

                        const dayData = planmemo.filter(item => item.date === i);
                        dayData.forEach(data => {
                            const li = document.createElement('li');
                            createtag(li, data.title, data.imgSrc, data.addr); // createtag 함수 사용
                            const input = document.createElement('input');
                            input.type = 'text';
                            input.value = data.inputValue; // inputValue 설정
                            li.appendChild(input);

                            const deleteBtn = document.createElement('input');
                            deleteBtn.type = "button";
                            deleteBtn.value = "삭제";
                            deleteBtn.addEventListener('click', deleteTourMemo);
                            li.appendChild(deleteBtn);

                            newUl.appendChild(li); // li를 newUl에 추가
                        });
                        }

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

            let planData = JSON.parse('${planData}');
            let planmemo = planData.tourMemoData;
            const planTitle = planData.tourTitleData;
            init(planTitle);

            function init(planTitle) {
                console.log("d");
                tour_title.value = planTitle.tour_title;
                startDateInput.value = planTitle.startDate;
                endDateInput.value = planTitle.endDate;
                document.getElementById('planNo').value = planTitle.planNo;
                console.log(planmemo);
                updateDuration();
                planmemo = null;
            }
    });

        // 카카오 map을 띄우는 곳
        var mapcontainer = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
        var options = { //지도를 생성할 때 필요한 기본 옵션
	        center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
	        level: 3 //지도의 레벨(확대, 축소 정도)
        };

        var map = new kakao.maps.Map(mapcontainer, options); //지도 생성 및 객체 리턴
</script>