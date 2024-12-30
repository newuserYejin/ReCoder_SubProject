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