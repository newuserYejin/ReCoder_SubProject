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

            todayAttPtag.textContent = "오늘의 근태 : " + data.attStatus;
        })
        .catch(error => {
            console.error("Fetch error: ", error);
        });
}