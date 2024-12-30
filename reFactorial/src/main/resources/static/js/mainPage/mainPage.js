const todayAttPtag = document.querySelector("#todayAtt")

window.onload = function () {
    fetch("/attendance/getTodayAttendance", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log("근태 data: ", data)
            
            let att = data.attStatus;
            
            if (att == '출근' || att == "정상 출근"){
                att = "정상 출근"
            }

            todayAttPtag.textContent = att;
        })
        .catch(error => {
            console.error("Fetch error: ", error);
        });

    let lat;
    let lon;
    navigator.geolocation.getCurrentPosition((position) => {
        lat = position.coords.latitude;
        lon = position.coords.longitude;
        console.log("latitude : ",position.coords.latitude," longitude : ", position.coords.longitude);

        fetch("/api/weather-key", {
            method: "GET"
        }).then(response => response.text())
            .then(key =>{
                console.log("api key : ",key)

                if (key){
                    fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${key}`,{
                        method: 'GET'
                    }).then(response => response.json())
                        .then(data =>{
                            console.log("날씨 데이터 : ", data)

                            const maxTemp = data.main.temp_max;
                            const minTemp = data.main.temp_min;
                            const icon = data.weather[0].icon;

                            setWeather(maxTemp,minTemp,icon)
                        })
                } else {
                    console.log("api key 없음")
                }

            });

    });

    const profileImg = document.querySelector(".profileImg img");

    if (profileUrl != null && profileUrl.trim() != ''){
        profileImg.src = `/uploadImg/${profileUrl}`;
    }

}

function setWeather (maxTemp,minTemp,icon){
    maxTemp = (maxTemp -273.15).toFixed(1)
    minTemp = (minTemp -273.15).toFixed(1)

    const weatherAPIBox = document.querySelector(".WeatherAPIBox");

    const result = `
                <div class="weatherImg">
                    <img src="https://openweathermap.org/img/wn/${icon}@4x.png">
                </div>
                <div class="weatherTemp">
                    <div>${minTemp} <span></span> /</div>
                    <div> ${maxTemp} <span></span> </div>
                </div>
    `
    weatherAPIBox.innerHTML =''
    weatherAPIBox.innerHTML = result
}

function redirectBasedOnAuth() {
    // LoginUserInfo.viewAuth 값을 서버에서 미리 렌더링하여 가져옵니다.
     // 기본값은 'USER'로 설정
    if (viewAuth === 'ADMIN') {
        location.href = 'admin/main';
    } else {
        location.href = 'user/myPage';
    }
}


let voteContent = 0;

const prevBtn = document.querySelector("#prevBtn")
const nextBtn = document.querySelector("#nextBtn")
const voteContentBoxBox = document.querySelectorAll(".voteContentBoxBox");

function updateVisibility() {
    // 모든 div를 숨기고
    voteContentBoxBox.forEach(div => {
        if (parseInt(div.getAttribute("data-num")) === voteContent) {
            div.style.display = "flex"; // voteContent와 일치하면 보이기
        } else {
            div.style.display = "none"; // 아니면 숨기기
        }
    });
}

updateVisibility();

voteContentBoxBox.forEach((box, index) => {
    const prevBtn = document.getElementById(`prevBtn_${index}`);
    const nextBtn = document.getElementById(`nextBtn_${index}`);

    if (prevBtn) {
        prevBtn.addEventListener("click", () => {
            if (voteContent > 0) {
                voteContent--; // voteContent 감소
                updateVisibility();
            }
            // 처음에 왔을 때, 이전 버튼 비활성화
            if (voteContent === 0) {
                prevBtn.disabled = true;
            } else {
                prevBtn.disabled = false;
            }
        });
    }

    if (nextBtn) {
        nextBtn.addEventListener("click", () => {
            if (voteContent < voteContentBoxBox.length - 1) {
                voteContent++; // voteContent 증가
                updateVisibility();
            }
            // 마지막에 왔을 때, 다음 버튼 비활성화
            if (voteContent === voteContentBoxBox.length - 1) {
                nextBtn.disabled = true;
            } else {
                nextBtn.disabled = false;
            }
        });
    }
});