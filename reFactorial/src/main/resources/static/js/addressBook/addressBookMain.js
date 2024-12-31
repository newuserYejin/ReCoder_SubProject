// 검색 상태 변수
let debounceTimeout; // 디바운스 타이머
let preValue = ""; // 이전 검색어

document.addEventListener('DOMContentLoaded', function () {

    const searchInput = document.getElementById('search-input');

    // 페이지 로드 시 전체 직원 조회 실행
    fetchEmployees();

    // 검색 입력에서 입력할 때마다 실시간 검색 실행
    searchInput.addEventListener('input', (event) => {
        clearTimeout(debounceTimeout); // 이전 타이머 취소
        debounceTimeout = setTimeout(() => {
            const presentValue = event.target.value.trim();
            if (preValue !== presentValue) {
                if (presentValue === "") {
                    fetchEmployees(); // 입력값이 비어 있으면 전체 조회 실행
                } else {
                    searchEmployees(presentValue); // 검색어가 변경되었을 때 fetch 실행
                }
                preValue = presentValue; // 이전 값 갱신
            }
        }, 200); // 200ms 디바운스
    });
});

// 전체 직원 조회 함수
function fetchEmployees() {
    fetch('/addressBook/employees')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => renderEmployees(data))
        .catch(error => console.error('Error fetching employees:', error));
}

// 검색 실행 함수
function searchEmployees(keyword) {
    fetch(`/addressBook/search?keyword=${keyword}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => renderEmployees(data))
        .catch(error => {
            console.error('Error fetching employees:', error);
            // 에러 메시지를 사용자에게 표시
            const tableBody = document.getElementById('employee-list');
            tableBody.innerHTML = `<div class="table-row">검색 중 오류가 발생했습니다.</div>`;
        });
}

// 데이터를 테이블에 렌더링하는 함수
function renderEmployees(data) {
    const tableBody = document.getElementById('employee-list');
    tableBody.innerHTML = ""; // 기존 데이터를 초기화

    if (data.length === 0) {
        tableBody.innerHTML = `<div class="table-row">검색 결과가 없습니다.</div>`;
        return;
    }

    data.forEach(employee => {
        const row = document.createElement('div');
        row.className = "table-row";

        function formatPhoneNumber(phoneNumber) {
            if (!phoneNumber) return '';
            return phoneNumber.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3'); // 전화번호 포맷팅
        }

        row.innerHTML = `
            <div class="table-cell">${employee.id}</div>
            <div class="table-cell">${employee.name}</div>
            <div class="table-cell">${employee.deptName}</div>
            <div class="table-cell">${employee.positionName}</div>
            <div class="table-cell">${employee.email}</div>
            <div class="table-cell">${formatPhoneNumber(employee.phone)}</div>
        `;
        tableBody.appendChild(row);
    });
}

// 스크롤 감지 및 버튼 표시
const scrollableElement = document.querySelector('.main-content'); // 스크롤이 발생하는 요소 선택

scrollableElement.addEventListener('scroll', function () {
    console.log('Scroll event triggered on .main-content'); // 스크롤 이벤트 실행 여부 확인
    const scrollToTopButton = document.getElementById('scrollToTop');
    if (scrollableElement.scrollTop > 200) { // 스크롤 위치가 200px 이상일 때 버튼 표시
        scrollToTopButton.style.display = 'block';
    } else {
        scrollToTopButton.style.display = 'none';
    }
});

// 위로 이동 버튼 클릭 이벤트
document.getElementById('scrollToTop').addEventListener('click', function () {
    scrollableElement.scrollTo({
        top: 0, // 최상단으로 이동
        behavior: 'smooth' // 부드럽게 이동
    });
});







// 눈이 내립니다~
function createSnowflake() {
    const snowflake = document.createElement('div');
    snowflake.className = 'snowflake';

    // 랜덤 특성 부여
    const left = Math.random() * 100; // 랜덤 가로 위치
    const size = Math.random() * 10 + 15; // 15-25px 크기
    const duration = Math.random() * 5 + 8; // 8-13초 낙하 시간
    const color = [
        'rgba(176, 224, 230, 0.4)', // 연한 하늘색
        'rgba(173, 216, 230, 0.4)', // 밝은 하늘색
        'rgba(135, 206, 235, 0.4)', // 스카이블루
        'rgba(176, 196, 222, 0.4)', // 연한 스틸블루
        'rgba(188, 212, 230, 0.4)'  // 파스텔 하늘색
    ][Math.floor(Math.random() * 5)];

    // 랜덤 눈송이 모양
    snowflake.innerHTML = ['❄', '✦', '❅', '✧'][Math.floor(Math.random() * 4)];

    // 스타일 적용
    snowflake.style.left = `${left}%`;
    snowflake.style.fontSize = `${size}px`;
    snowflake.style.color = color;
    snowflake.style.animationDuration = `${duration}s`;

    // 약간의 흔들림 효과 추가
    const wobble = Math.random() * 20 - 10;
    snowflake.style.animationName = `snowfall-${wobble}`;

    // 컨테이너에 추가
    document.getElementById('snowContainer').appendChild(snowflake);

    // 애니메이션 끝나면 제거
    setTimeout(() => {
        snowflake.remove();
    }, duration * 1000);
}

// 초기 눈송이들 생성
for(let i = 0; i < 50; i++) {  // 초기눈송이 갯수
    setTimeout(createSnowflake, Math.random() * 8000);  //숫자를 줄이면 더 빠르게 초기 눈송이가 생성됨
                                                        //숫자를 키우면 더 천천히 생성됨
}

// 계속해서 새로운 눈송이 생성  클수록 조금
setInterval(createSnowflake, 800);

// 눈의 양: setInterval의 시간(150)을 조절
// 눈의 크기: size 변수의 범위(15-25px) 조절
// 낙하 속도: duration 변수의 범위(8-13초) 조절
// 색상: color 배열의 색상들 수정

