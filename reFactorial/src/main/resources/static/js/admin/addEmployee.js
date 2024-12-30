// 주민번호 예외처리
const empNo = document.getElementById("empNo")
const empPwdInput = document.getElementById("empPwd");
const empName = document.getElementById("empName");

empNo.addEventListener("input",(event)=>{
    let value = empNo.value.replace(/[^0-9]/g, ''); // 숫자 외 제거

    console.log(value)

    if (value.length > 6){
        value = value.slice(0, 6) + '-' + value.slice(6);
    }

    empNo.value = value;
})

empNo.addEventListener("blur",(event)=>{
    const birthDate = event.currentTarget.value.substring(0,6)
    empPwdInput.value=birthDate;
})

// 이름 예외처리
function validateName(event) {
    const name = event.target.value;
    if (/\d/.test(name)) {
        event.target.value = name.replace(/\d/g, '');
    }
}

// 전화번호 검증
function formatPhoneNumber(event) {
    let phone = event.target.value.replace(/\D/g, ''); // 숫자만 남김
    if (phone.length < 4) {
        event.target.value = phone;
    } else if (phone.length < 8) {
        event.target.value = phone.replace(/(\d{3})(\d{1,4})/, '$1-$2');
    } else if (phone.length < 12) {
        event.target.value = phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    }
}

// 이메일 형식 검증
function validateEmail(event) {
    const email = event.target.value;
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    const error = document.querySelector(".error");

    if (!emailPattern.test(email)) {
        error.style.display = "block";  // 오류 메시지 보이기
    } else {
        error.style.display = "none";   // 오류 메시지 숨기기
    }
}

// 연차 입력 시 숫자만 입력
function validateAnnualLeave(event) {
    const annualLeave = event.target.value;
    if (/\D/.test(annualLeave)) {
        event.target.value = annualLeave.replace(/\D/g, ''); // 숫자 이외의 문자 제거
    }
}

