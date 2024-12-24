const changePW = document.querySelector("#changePW")
const comparePW = document.querySelector("#comparePW")

comparePW.addEventListener("input", validatePasswords)

// 비밀번호 변경 모달 변경 비밀번호 일치 유효성 검사
function validatePasswords() {
    const changePWValue = changePW.value;
    const comparePWValue = comparePW.value;

    if (changePWValue !== comparePWValue) {
        addErrorClasses();
    } else {
        removeErrorClasses();
    }
}

function addErrorClasses() {
    // 'error' 클래스 추가
    changePW.classList.add("error");
    comparePW.classList.add("error");

    // p태그 지칭
    changePW.nextElementSibling.classList.add("error");
    comparePW.nextElementSibling.classList.add("error");
}

function removeErrorClasses() {
    // 'error' 클래스 삭제
    changePW.classList.remove("error");
    comparePW.classList.remove("error");

    // p태그 지칭
    changePW.nextElementSibling.classList.remove("error");
    comparePW.nextElementSibling.classList.remove("error");
}

// 현재 비밀번호 일치 확인
const matchPWBtn = document.querySelector("#matchPW");
const Dataform = document.querySelector("#changePWForm")
const presentPWTag = document.querySelector("#presentPW")

matchPWBtn.addEventListener("click", (e) => {
    e.preventDefault();

    const presentPW = document.querySelector("#presentPW").value;

    fetch('/user/matchPW', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            presentPW: presentPW
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log("data: ", data)

            if (data) {
                presentPWTag.classList.remove("error");
                presentPWTag.nextElementSibling.classList.remove("error");
                Dataform.submit();
            } else {
                // p태그 지칭
                presentPWTag.classList.add("error");
                presentPWTag.nextElementSibling.classList.add("error");
            }
        })
        .catch(error => console.log(error));
})

// 개인정보 수정하기 코드 시작
const personal_InfoBtn = document.querySelector(".personal_InfoBtn");
// 비번 변경 버튼
const personal_InfoBtnBox = document.querySelector(".personal_InfoBtnBox");
const modifyInfoSave = document.querySelector(".modifyInfoSave")

const modifyInfoList = document.querySelectorAll(".modifyInfo");

// 프로필 사진
const profileImg = document.querySelector("label[for='profileImg']")

// input disabled 삭제하기
personal_InfoBtn.addEventListener("click", () => {
    personal_InfoBtnBox.style.display = "none";
    modifyInfoSave.style.display = "block"
    personal_InfoBtn.style.display = "none"
    profileImg.style.display="flex"

    modifyInfoList.forEach(info => {
        info.disabled = false;
    })
})

// 개인정보 수정하기
modifyInfoSave.addEventListener("click", () => {

    // 입력된 값을 가져와서 데이터 객체로 구성
    let email = document.querySelector("#email").value;
    let phone = document.querySelector("#phone").value;
    let address = document.querySelector("#address").value;
    let profileImg = document.querySelector("#profileImg").files[0];

    // 이메일 값이 서버 값과 같으면 null로 처리
    if (currentEmail.trim() === email.trim()) {
        email = null;
    }
    if (currentPhone.trim() === phone.trim()) {
        phone = null;
    }
    if (currentAddress.trim() === address.trim()) {
        address = null;
    }

    const formData = new FormData();

    formData.append("email", email);
    formData.append("phone", phone);
    formData.append("address", address);

    if (profileImg) {
        formData.append("profileImgList", profileImg); // 파일 추가
    } else{
        formData.append("profileImgList", null); // 파일 추가
    }

    console.log("profileImgList : ",formData.get('profileImgList'))

    console.log("formData: ",formData)

    fetch('/user/updatePersonalInfo', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log(data)

            // 데이터가 성공적으로 처리된 경우 페이지 새로고침
            if (data.result > 0) {
                location.reload(); // 페이지 새로 고침
            } else if (data.result === 0) {
                location.reload(); // 페이지 새로 고침
                // alert("데이터 업데이트 내용 없음");
            } else {
                alert("업데이트 오류")
            }
        })
})

document.addEventListener('DOMContentLoaded', function() {
    let currentStart;
    var calendarEl = document.getElementById('calendar');

    function addNewEvent(title, startDate, color) {
        calendar.addEvent({
            title: title,
            start: startDate,
            backgroundColor: color,
            textColor: 'white'
        });
    }

    var calendar = new FullCalendar.Calendar(calendarEl, {

        initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
        headerToolbar: { // 헤더에 표시할 툴 바
            left: 'prev',
            center: 'title',
            right: 'next today'
        },
        titleFormat: function(date) {
            return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월';
        },
        selectable: false, // 달력 일자 드래그 설정가능
        locale: 'ko', // 한국어 설정
        fixedWeekCount: false,
        events: []
    });

    // 초기 값 설정
    var initialDate = calendar.getDate(); // 캘린더 초기 날짜 가져오기

    // datesSet 이벤트로 업데이트 및 출력
    calendar.on('datesSet', function(info) {
        // 새로운 계산
        currentStart = info.start;

        // 서버로 보내기
        fetch('/attendance/getattendance',{
            method : 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                currentStart : currentStart
            })
        })
            .then(response => response.json())
            .then(data => {
                console.log("근태 데이터 : ",data)

                // 모든 이벤트를 삭제하는 방법
                calendar.getEvents().forEach(event => event.remove());

                data.forEach(data =>{
                    let backgroundColor = data.attStatus === "지각" ? '#ff7b00' :
                        data.attStatus === "연차" ? "#3c73aa" :
                            data.attStatus === "반차" ? "#77b6ea" : "#a7c957";


                    addNewEvent(data.attStatus,data.attDate,backgroundColor);
                })

            })
            .catch(error => console.error("데이터 요청 실패:", error));
    });

    calendar.render();
});


// 연차 관련 박스 내용 바꾸기

const annualLeaveDiv = document.querySelector('.annualLeave')
const work_monthDiv = document.querySelector(".work_month")

const today = new Date(); // 오늘 날짜
const empJoinedDate = new Date(empJoined);

annualLeaveDiv.textContent = (annualLeave-usedAnnualLeave) + "일";

// 유효한 Date 객체인지 확인
if (isNaN(empJoinedDate)) {
    console.error("empJoined 값이 유효하지 않은 날짜 형식입니다:", LoginUserInfo.empJoined);
} else {
    // 년도와 월 차이를 계산
    const yearDiff = today.getFullYear() - empJoinedDate.getFullYear();
    const monthDiff = today.getMonth() - empJoinedDate.getMonth();

    // 전체 개월 수 계산
    let totalMonths = yearDiff * 12 + monthDiff;

    // 근무 시작일이 오늘보다 이후라면 (미래 날짜 처리)
    if (empJoinedDate > today) {
        totalMonths = 0;
    }

    work_monthDiv.textContent = totalMonths + "개월";
}

