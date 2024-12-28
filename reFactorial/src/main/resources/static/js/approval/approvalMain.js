function createSnowfall() {
    const snowCount = 200;
    const body = document.body;

    for (let i = 0; i < snowCount; i++) {
        const snow = document.createElement('div');
        snow.classList.add('snow');

        // 화면 전체 너비에 걸쳐 랜덤 분포
        const randomX = Math.random() * (window.innerWidth || document.documentElement.clientWidth);
        const randomScale = Math.random() * 0.5 + 0.5;
        const fallDuration = Math.random() * 20 + 10;
        const horizontalOffset = Math.random() * 200 - 100;

        // 다양한 색상 추가
        const randomColor = getRandomColor();
        snow.style.background = randomColor;

        snow.style.left = `${randomX}px`;
        snow.style.top = `-10px`;
        snow.style.transform = `scale(${randomScale})`;
        snow.style.animation = `fall ${fallDuration}s linear infinite`;

        const keyframeStyle = document.createElement('style');
        keyframeStyle.innerHTML = `
                @keyframes fall {
                    0% {
                        transform: translate(${randomX}px, -10px) scale(${randomScale});
                    }
                    100% {
                        transform: translate(${randomX + horizontalOffset}px, 100vh) scale(${randomScale});
                    }
                }
            `;

        document.head.appendChild(keyframeStyle);
        body.appendChild(snow);
    }
}

// 랜덤 색상 생성 함수 추가
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

// 페이지 완전히 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {
    createSnowfall();
});

// 창 크기 변경 시 눈송이 재생성
window.addEventListener('resize', function() {
    // 기존 눈송이 제거
    const existingSnow = document.querySelectorAll('.snow');
    existingSnow.forEach(snow => snow.remove());

    // 새 눈송이 생성
    createSnowfall();
});