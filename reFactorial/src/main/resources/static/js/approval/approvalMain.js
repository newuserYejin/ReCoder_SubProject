function showTab(tabName) {
    // 모든 탭 내용을 숨김
    document.querySelectorAll('.tab-content').forEach(content => {
        content.classList.remove('active');
    });

    // 모든 탭 버튼의 active 상태 제거
    document.querySelectorAll('.tab-button').forEach(button => {
        button.classList.remove('active');
    });

    // 선택된 탭 내용을 보여줌
    document.getElementById(tabName + '-tab').classList.add('active');

    // 선택된 탭 버튼을 active 상태로 변경
    document.querySelector(`[onclick="showTab('${tabName}')"]`).classList.add('active');
}

// 페이지 로드 시 기본 탭 활성화
document.addEventListener('DOMContentLoaded', function() {
    showTab('draft'); // 기본으로 기안 문서 탭 표시
});