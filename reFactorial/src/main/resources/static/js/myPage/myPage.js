
const changePW = document.querySelector("#changePW")
const comparePW = document.querySelector("#comparePW")

comparePW.addEventListener("input",validatePasswords)

function validatePasswords() {
    const changePWValue = changePW.value;
    const comparePWValue = comparePW.value;

    if (changePWValue !== comparePWValue) {
        addErrorClasses();
    } else {
        removeErrorClasses();
    }
}

function addErrorClasses(){
    // 'error' 클래스 추가
    changePW.classList.add("error");
    comparePW.classList.add("error");

    // p태그 지칭
    changePW.nextElementSibling.classList.add("error");
    comparePW.nextElementSibling.classList.add("error");
}

function removeErrorClasses(){
    // 'error' 클래스 삭제
    changePW.classList.remove("error");
    comparePW.classList.remove("error");

    // p태그 지칭
    changePW.nextElementSibling.classList.remove("error");
    comparePW.nextElementSibling.classList.remove("error");
}

const matchPWBtn = document.querySelector("#matchPW");
const Dataform = document.querySelector("#changePWForm")
const presentPWTag = document.querySelector("#presentPW")

matchPWBtn.addEventListener("click",(e) =>{
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
        .then(data =>{
            console.log("data: ",data)

            if (data){
                presentPWTag.classList.remove("error");
                presentPWTag.nextElementSibling.classList.remove("error");
                Dataform.submit();
            } else{
                // p태그 지칭
                presentPWTag.classList.add("error");
                presentPWTag.nextElementSibling.classList.add("error");
            }
        })
        .catch(error => console.log(error));
})