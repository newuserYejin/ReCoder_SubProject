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
}