
// 삭제 관련 js

document.addEventListener("DOMContentLoaded",()=>{
    // 예약 내역에 종료 시간 1시간 추가해서 출력하기
    const endTimeList = document.querySelectorAll(".endTime");

    endTimeList.forEach(endTime =>{
        const time = endTime.dataset.endtime

        // 시간 포맷 변경 함수 호출
        const adjustedTime = formatAndAdjustTime(time);

        endTime.textContent = adjustedTime;
    })

// 시간을 1시간 증가시키는 함수
    function formatAndAdjustTime(originalTime) {
        // 입력값을 Date 객체로 파싱 (hh:mm 형식)
        const timeParts = originalTime.split(":");
        const date = new Date();
        date.setHours(timeParts[0], timeParts[1], 0, 0);  // 시, 분 설정

        // 1시간 증가
        date.setHours(date.getHours() + 1);

        // 변경된 시간을 hh:mm 형식으로 반환
        const adjustedHours = String(date.getHours()).padStart(2, '0');
        const adjustedMinutes = String(date.getMinutes()).padStart(2, '0');
        const fomattingTime =  `${adjustedHours}:${adjustedMinutes}`;

        return fomattingTime
    }
})

const deleteConfirmModal = document.querySelector("#deleteConfirmModal")
const deleteConfirmModalInstance = new bootstrap.Modal(deleteConfirmModal);
const acceptBtn = document.querySelector(".acceptBtn");

const reservationCancelBtnList = document.querySelectorAll(".reservationCancelBtn");

reservationCancelBtnList.forEach(reservationCancelBtn =>{
    reservationCancelBtn.addEventListener("click",()=>{

        deleteConfirmModalInstance.show();

        acceptBtn.addEventListener("click",()=>{
            fetch('/booking/deleteReservation',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                // 데이터 숨길때 JSON.stringify, 아니면 new URLSearchParams
                body: JSON.stringify({
                    reserveid : reservationCancelBtn.dataset.reserveid
                })
            }).then(response => response.json())
                .then(data =>{
                    if (data.result > 0){
                        location.reload();
                    }

                })
        })
    })

})

