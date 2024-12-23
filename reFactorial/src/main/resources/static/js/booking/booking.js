// 시간 선택 프론트에서 제어하기

const itemArray = document.querySelectorAll("input[type=checkbox]");
let disabledIndexes = [];

    // disabled 상태인 체크박스 확인
itemArray.forEach((item, index) => {
    if (item.disabled) {
        disabledIndexes.push(index);
    }
});

let start = null;
let end = null;


// 시작시간 부터 종료시간 내에 disabled 시간이 들어있는지 확인
// 여기가 핵심
function isRangeValid(rangeStart, rangeEnd) {
    for (let i = rangeStart; i <= rangeEnd; i++) {
        if (disabledIndexes.includes(i)) {
            return false;
        }
    }
    return true;
}

    // 전체 초기화
function resetSelection() {
    start = null;
    end = null;
    itemArray.forEach((checkbox) => (checkbox.checked = false));
}

function handleCheckboxClick(check, checkIndex) {
    console.log("\n체크박스 클릭:", checkIndex);
    console.log("현재의 start:", start, "end:", end);
    console.log("disabledIndexes:", disabledIndexes);

    // disabled 는 클릭자체를 막기
    if (disabledIndexes.includes(checkIndex)) {
        alert("선택한 시간대는 사용할 수 없습니다.");
        check.checked = false;
        return;
    }

    // 시작시간 해지 또는 종료시간 해지
    if (checkIndex === start || checkIndex === end) {
        console.log("현재 선택된 값을 해제:", checkIndex);
        check.checked = false;

        // 1개만 선택되어 있을 때
        if (checkIndex === start && start === end) {
        resetSelection();
    } else if (checkIndex === start) {
        // 시작시간 해지
        start = end;
    } else if (checkIndex === end) {
        // 종료시간 해제
        end = start;
    }
    return;
}

    // 처음 start 와 end 설정
    if (start === null) {
        start = checkIndex;
        end = checkIndex;
        console.log("첫 번째 클릭 - start와 end 설정:", start);
    } else {
        // 시작시간 보다 앞시간 선택
        if (checkIndex < start) {
            if (isRangeValid(checkIndex, start)) {
                start = checkIndex;
                console.log("start 갱신:", start, "end:", end);
            } else {
                alert("선택한 구간에 사용할 수 없는 시간이 포함되어 있습니다.");
                check.checked = false;
            }
        } else if (checkIndex > end) {          // 시작시간 보다 뒷시간 선택
            if (isRangeValid(end, checkIndex)) {
                end = checkIndex;
            } else {
                alert("선택한 구간에 사용할 수 없는 시간이 포함되어 있습니다.");
                check.checked = false;
            }
        }
    }

        // start와 end가 설정되었을 때, 중간 값들을 모두 checked 처리
    if (start !== null && end !== null) {

        for (let i = start; i <= end; i++) {
            if (!disabledIndexes.includes(i)) {
                itemArray[i].checked = true;
            }
        }
    }
}

    // 각 체크박스를 클릭할 때 handleCheckboxClick 호출
itemArray.forEach((checkbox, index) => {
    checkbox.addEventListener('click', function () {
        handleCheckboxClick(this, index);
    });
});

// 캘린더 연결

// 오늘 날짜
const currentDate = new Date();

// yyyy-MM-dd
function dateFormate(data){
    const result = `${data.getFullYear()}-${data.getMonth() + 1}-${data.getDate()}`

    return result
}

var calendarEl = document.getElementById('calendar');

var calendar = new FullCalendar.Calendar(calendarEl, {

    initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
    headerToolbar: { // 헤더에 표시할 툴 바
        left: 'prev',
        center: 'title',
        right: 'next today'
    },
    validRange: {
        start: dateFormate(currentDate)
    },
    titleFormat: function(date) {
        return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월';
    },
    select: function(start) {
        setDate(start)
    },
    selectable: true, // 날짜 선택 여부 설정
    locale: 'ko', // 한국어 설정
    fixedWeekCount: false,
    events: []
});

calendar.render();

const selectDay = document.querySelector(".selectDay");
let selectDayValue = dateFormate(currentDate);
selectDay.textContent = `예약 날짜 : ${selectDayValue}`;

function setDate(dayInfo){
    console.log("에약 일 : ", dayInfo.startStr)
    selectDayValue = dayInfo.startStr;
    selectDay.textContent = `예약 날짜 : ${selectDayValue}`
}

const reserveBtn = document.querySelector("#reserveBtn");
const urlSearch = new URLSearchParams(location.search);

reserveBtn.addEventListener("click",(e)=>{
    e.preventDefault();

    console.log(selectDayValue)

    fetch("reserve",{
        method : 'POST',
        // 데이터 숨길때 'Content-Type': 'application/json', 아니면 'Content-Type': 'application/x-www-form-urlencoded'
        headers: {
            'Content-Type': 'application/json',
        },
        // 데이터 숨길때 JSON.stringify, 아니면 new URLSearchParams
        body: JSON.stringify({
            reservationDate : selectDayValue,
            reservationStartTime: itemArray[start].value,
            reservationEndTime: itemArray[end].value,
            conferenceRoomNo:urlSearch.get('roomNo')
        })
    }).then(response => response.json())
        .then(data =>{
            console.log(data)
            location.reload();
        })
})

// 페이지가 로드 되자 마자 데이터 가져오기

document.addEventListener("DOMContentLoaded",()=>{
    const roomNo = urlSearch.get('roomNo')

    fetchData(dateFormate(currentDate),roomNo);
})

function fetchData(date,roomNo){
    console.log("데이터 가져오기 : ",date," ",roomNo)

    fetch(`/booking/getReserveByRoomNo?selectedDate=${date}&roomNo=${roomNo}`,{
        method : 'GET',
        headers:{
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
        .then(data =>{
            console.log("fetch 결과 : ",data)

            if (data.reservationList){
                disableReservedTimes(data.reservationList)
            }
        })

}

// 예약된 시간 비활성화
function disableReservedTimes(reservationList) {
    reservationList.forEach(reservation => {
        // 예약의 시작 시간과 끝 시간 10 진수로 가져오기
        const startTime = parseInt(reservation.reservationStartTime.split(':')[0], 10);
        const endTime = parseInt(reservation.reservationEndTime.split(':')[0], 10);

        // 해당 시간과 사이 시간 비활성화
        for (let time = startTime; time <= endTime; time++) {
            const input = document.querySelector(`.timeList input[value="${time.toString()}:00:00"]`);
            if (input) {
                input.disabled = true;
            }
        }
    });
}