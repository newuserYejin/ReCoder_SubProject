// 제품 데이터 초기화
const products = /*[[${products}]]*/ [];
let currentRotation = 0;
let selectedIndex = 0;

// 캐러셀 초기화
function initializeCarousel() {
    const carousel = document.querySelector('.carousel');
    const angleStep = 360 / products.length;
    const radius = 1000; // 카드와의 간격
    const verticalOffset = -50; // 카드 위치 조정

    // 기존 캐러셀 초기화
    carousel.style.transform = `rotateY(${currentRotation}deg)`;
    carousel.innerHTML = '';

    // 제품 카드 추가
    products.forEach((product, index) => {
        const cardWrapper = document.createElement('div');
        cardWrapper.className = 'card-wrapper';

        const angle = angleStep * index;
        cardWrapper.style.transform = `rotateY(${angle}deg) translateZ(${radius}px) translateY(${verticalOffset}px)`;

        const card = document.createElement('div');
        card.className = 'product-card';
        card.innerHTML = `
            <img src="${product.image}" alt="${product.name}">
            <h3>${product.name}</h3>
            <p class="price">${product.price}</p>
            <p>${product.description}</p>
        `;
        cardWrapper.appendChild(card);

        // 카드의 투명도 조정
        cardWrapper.style.opacity = selectedIndex === index ? '1' : '0.7';
        carousel.appendChild(cardWrapper);
    });
}

// 캐러셀 회전
function rotateCarousel(direction) {
    currentRotation += -direction * (360 / products.length);
    const carousel = document.querySelector('.carousel');
    carousel.style.transform = `rotateY(${currentRotation}deg)`;

    selectedIndex = (selectedIndex + direction + products.length) % products.length;

    // 카드 방향 및 투명도 업데이트
    document.querySelectorAll('.card-wrapper').forEach((wrapper, index) => {
        const card = wrapper.querySelector('.product-card');
        const angle = (360 / products.length) * index;

        card.style.transform = `rotateY(${-angle - currentRotation}deg)`;
        wrapper.style.opacity = selectedIndex === index ? '1' : '0.7';
    });
}

// 제품 데이터 가져오기
async function fetchProducts() {
    const response = await fetch('/zasaPage/product');
    const productsData = await response.json();
    console.log("가져온 제품 데이터:", productsData);

    products.length = 0; // 기존 데이터 초기화
    products.push(...productsData); // 새 데이터 추가

    initializeCarousel(); // 캐러셀 초기화
}

// 새 제품 등록 요청
async function addProduct() {
    const newProduct = {
        name: document.getElementById("prodName").value,
        description: document.getElementById("prodDescription").value,
        price: document.getElementById("prodPrice").value,
        image: document.getElementById("prodImage").value,
        category: document.getElementById("prodCategory").value
    };

    console.log("등록 요청 데이터:", newProduct);

    const response = await fetch('/admin/addProduct', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newProduct),
    });

    const result = await response.text();
    console.log("서버 응답:", result);

    if (result === "success") {
        alert("제품 등록 성공!");
        fetchProducts(); // 제품 리스트 갱신
    } else {
        alert("제품 등록 실패!");
    }
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    const carouselElement = document.querySelector('.carousel');
    if (carouselElement) {
        fetchProducts(); // 캐러셀 초기화
    }
});