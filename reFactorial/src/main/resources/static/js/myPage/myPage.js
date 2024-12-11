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

// input disabled 삭제하기
personal_InfoBtn.addEventListener("click", () => {
    personal_InfoBtnBox.style.display = "none";
    modifyInfoSave.style.display = "block"
    personal_InfoBtn.style.display = "none"

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

    console.log("js 파일 내부 currentEmail : ", currentEmail)
    console.log("js 파일 내부 currentPhone : ", currentPhone)
    console.log("js 파일 내부 currentAddress : ", currentAddress)

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

    console.log("null email : ", email)
    console.log("null phone : ", phone)
    console.log("null address : ", address)

    fetch('/user/updatePersonalInfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            phone: phone,
            address: address
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data)

            // 데이터가 성공적으로 처리된 경우 페이지 새로고침
            if (data.result > 0) {
                location.reload(); // 페이지 새로 고침
            } else if (data.result === 0) {
                alert("데이터 업데이트 내용 없음");
            } else {
                alert("업데이트 오류")
            }
        })
})
