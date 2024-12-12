
var swiper = new Swiper(".mySwiper", {
    effect: "cube",
    loop: true,
    // centeredSlides: true,
    grabCursor: true,
    slidesPerView: "auto",
    cubeEffect: {
        shadow: true,
        slideShadows: true,
        shadowOffset: 20,
        shadowScale: 0.8,
    },
    autoplay: {
        delay: 1500,
        disableOnInteraction: false
    },
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    }
});

// url 감지
urlSearch = new URLSearchParams(location.search);

// 로그인 오류 메세지 출력
const errorMsg = urlSearch.get('errorMessage');
const errorP = document.querySelectorAll(".loginBox p");

if (errorMsg){
    console.log("errorMsg: ",errorMsg);

    errorP.forEach( errorPtag =>{
        errorPtag.classList.add("error");
        errorPtag.textContent = errorMsg;
    })
} else{
    errorP.forEach( errorPtag =>{
        errorPtag.classList.remove("error");
        errorPtag.textContent = '';
    })
}

// 사원번호 저장 관련 쿠기 존재여부 확인
window.onload = function() {
    const savedMemberId = getCookie("member_id");
    if (savedMemberId) {
        document.getElementById("member_id").value = savedMemberId;
        document.getElementById("saveId").checked = true;
    }
};

// 쿠키에서 값 가져오기
// (^|;) => 쿠키의 시작 부분 또는 이전 쿠키와 구분된 곳을 찾기
// \\s* => 공백 처리
// ([^;]*) => 값 부분을 캡처하는 패턴, 세미콜론이 아닌" 문자
function getCookie(name) {
    const value = document.cookie.match('(^|;)\\s*' + name + '=([^;]*)');
    return value ? value.pop() : '';
}


