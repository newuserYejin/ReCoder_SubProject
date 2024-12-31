document.addEventListener('DOMContentLoaded', () => {
    // 상태 관리를 위한 변수들
    let currentQuestion = 0;
    let score = 0;
    let goldTickets = 0;
    let hintsUsed = 0;
    let showHint = false;
    let hintBubbleTimeout;
    let isQuizStarted = false;





    // 퀴즈 데이터
    const questions = [
        {
            question: "윌리웡카 초콜릿 공장의 첫 번째 규칙은 '상상력을 무한히 펼치기'입니다.",
            answer: true,
            explanation: "맞습니다! 상상력은 우리 공장의 가장 중요한 가치입니다.",
            hint: "♪ 상상이 현실이 되는 곳, 우리의 공장~ ♪"
        },
        {
            question: "초콜릿 레시피는 친구들에게 공유해도 됩니다.",
            answer: false,
            explanation: "절대 안됩니다! 비밀 레시피는 우리의 가장 소중한 자산입니다.",
            hint: "♪ 비밀이 새어나가면 마법이 사라져요~ ♪"
        },
        {
            question: "매일 아침 품질 검사를 위해 초콜릿을 맛보는 것은 필수입니다.",
            answer: true,
            explanation: "맞습니다! 완벽한 맛을 위해 검사는 필수죠!",
            hint: "♪ 달콤한 아침은 검사로 시작해요~ ♪"
        },
        {
            question: "움파룸파들에게는 매년 2주의 초콜릿 휴가가 주어집니다.",
            answer: true,
            explanation: "맞습니다! 일과 휴식의 균형이 중요하죠.",
            hint: "♪ 쉬었다 오면 더 달콤한 아이디어가 솟아나요~ ♪"
        },
        {
            question: "새로운 사탕 개발 시 안전 테스트 없이 바로 생산해도 됩니다.",
            answer: false,
            explanation: "절대 안됩니다! 안전은 가장 기본적인 원칙입니다.",
            hint: "♪ 안전한 사탕이 가장 달콤한 법이죠~ ♪"
        },
        {
            question: "초콜릿 강에서 수영하는 것은 위생 규정 위반입니다.",
            answer: true,
            explanation: "맞습니다! 위생 관리는 매우 중요합니다.",
            hint: "♪ 깨끗한 초콜릿이 가장 맛있어요~ ♪"
        },
        {
            question: "모든 직원은 매달 새로운 캔디 아이디어를 제출해야 합니다.",
            answer: true,
            explanation: "맞습니다! 창의적인 아이디어는 우리의 원동력입니다.",
            hint: "♪ 새로운 아이디어로 세상을 달콤하게~ ♪"
        },
        {
            question: "작업장에서는 일반 사복을 입어도 됩니다.",
            answer: false,
            explanation: "안됩니다! 특수 제작된 쫄쫄이 위생복만 착용해야 합니다.",
            hint: "♪ 특별한 옷을 입어야 특별한 맛이 탄생해요~ ♪"
        },
        {
            question: "판매되지 않은 초콜릿은 직원이 가져가도 됩니다.",
            answer: false,
            explanation: "안됩니다! 모든 제품은 정해진 절차에 따라 처리해야 합니다.",
            hint: "♪ 규칙이 있어야 마법이 지켜져요~ ♪"
        },
        {
            question: "공장 투어는 황금티켓 소지자만 참여할 수 있습니다.",
            answer: true,
            explanation: "맞습니다! 특별한 초대장이 있는 분들만 입장 가능합니다.",
            hint: "♪ 황금빛 티켓만이 문을 열 수 있죠~ ♪"
        }
    ];





    // 점수별 등급 정보
    const ranks = [
        {
            min: 9, max: 10,
            title: "윌리 평훈",
            description: "당신은 우수사원 축하드립니다!!!!!",
            badge: "👑"
        },
        {
            min: 7, max: 8,
            title: "똑똑한 청년",
            description: "분발해서 만점에도전하세여!",
            badge: "🍬"
        },
        {
            min: 5, max: 6,
            title: "초콜릿 장인",
            description: "달콤한 지식으로 가득한 당신!",
            badge: "🍬"
        },
        {
            min: 3, max: 4,
            title: "조금 모자라지만 착한친구",
            description: "일단 존재감은 확실한 당신! 재도전 고고!🤪",
            badge: "😛"
        },
        {
            min: 0, max: 2,
            title: "퇴사예정자",
            description: "짐을 싸서 퇴장해주세여..^",
            badge: "💀"
        },

    ];

    // 움파룸파 클래스 정의
    class OompaLoompa {
        constructor() {
            this.hintsRemaining = 3;
            this.element = null;
            // 여기에 이미지 경로 지정
            this.imageUrl = '/images/zasaPage/움파룸파.png';
        }

        render() {
            return `
            <div class="oompa-loompa" onclick="toggleHint()">
                <div class="oompa-loompa-character">
                   <img src="${this.imageUrl}" alt="움파룸파" class="oompa-loompa-image" />
                </div>
                <div class="hint-bubble ${showHint ? 'show' : ''}">
                    <div class="hint-text">
                        ${showHint ? questions[currentQuestion].hint : `
                            <div>힌트 남음</div>
                            <div class="font-bold">${3 - hintsUsed}개</div>
                        `}
                    </div>
                </div>
            </div>
        `;
        }
    }

    const oompaLoompa = new OompaLoompa();



    function startQuiz() {
        isQuizStarted = true;
        renderQuiz();
    }


    function renderStartScreen() {
        const quizCard = document.getElementById('quiz-card');
        quizCard.innerHTML = `
        <div class="start-screen">
            <div class="floating-candy candy-1">🍬</div>
            <div class="floating-candy candy-2">🍭</div>
            <div class="floating-candy candy-3">🍭</div>
            <div class="floating-candy candy-4">🍬</div>
            
            <div class="golden-ticket">
                <div class="ticket-content">
                    <h1 class="ticket-title">윌리웡카의 초콜릿 공장</h1>
                    <h2 class="ticket-subtitle">교육 퀴즈에 오신 것을 환영합니다!</h2>
                    <p class="ticket-text">뽀너스를 얻을 준비가 되셨나요?</p>
                    <button onclick="startQuiz()" class="start-button">
                        <span>퀴즈 시작하기</span>
                        <div class="button-stars"></div>
                    </button>
                </div>
            </div>
        </div>
    `;
    }


    // 퀴즈 카드 렌더링 함수
    function renderQuiz() {
        const quizCard = document.getElementById('quiz-card');
        quizCard.innerHTML = `
            <div class="progress-bar">
                <div class="progress-fill" style="width: ${(currentQuestion / questions.length) * 100}%"></div>
            </div>
            <div class="space-y-8 mt-6">
                <div class="flex justify-between items-center">
                    <div class="bg-gradient-to-r from-purple-100 to-pink-100 px-5 py-2 rounded-full shadow-lg">
                        <span class="text-purple-900 font-bold">${currentQuestion + 1}</span>
                        <span class="text-purple-700"> / ${questions.length}</span>
                    </div>
                    <div class="score-display">
                        <span class="font-bold text-yellow-900">점수: ${score}</span>
                    </div>
                </div>
                
                <div class="question-container">
                    <h2 class="text-2xl font-bold text-center text-purple-900">
                        ${questions[currentQuestion].question}
                    </h2>
                </div>
                
                <div class="answer-buttons">
                    <button class="answer-button true" onclick="handleAnswer(true)">O</button>
                    <button class="answer-button false" onclick="handleAnswer(false)">X</button>
                </div>

                ${oompaLoompa.render()}
            </div>
        `;

        // 힌트 버블 자동 숨김 설정
        if (showHint && hintBubbleTimeout) {
            clearTimeout(hintBubbleTimeout);
            hintBubbleTimeout = setTimeout(() => {
                showHint = false;
                renderQuiz();
            }, 3000);
        }
    }




    // 결과 화면 렌더링 함수
    function renderResult() {
        const rank = ranks.find(r => score >= r.min && score <= r.max);
        const quizCard = document.getElementById('quiz-card');
        quizCard.innerHTML = `
            <div class="result-container">
                 <h2 class="result-title">결과 발표! <span>${rank.badge}</span></h2>
                <div class="score-display">
                    <span class="text-3xl font-bold">${score} / ${questions.length}</span>
                </div>
                <div class="rank-info">
                    <h3 class="text-2xl font-bold">${rank.title}</h3>
                    <p>${rank.description}</p>
                </div>
                <div class="result-stats">
                    <div class="stat-item">
                        <div>성과급</div>
                        <div class="font-bold">${goldTickets}억</div>
                    </div>
                    <div class="stat-item">
                        <div>사용한 힌트</div>
                        <div class="font-bold">${hintsUsed}</div>
                    </div>
                </div>
            </div>
        `;
    }

    // 정답 처리 함수
    function handleAnswer(answer) {
        const correct = answer === questions[currentQuestion].answer;
        if (correct) {
            score++;
            goldTickets++;
        }

        showFeedback(correct);

        setTimeout(() => {
            if (currentQuestion < questions.length - 1) {
                currentQuestion++;
                showHint = false;
                renderQuiz();
            } else {
                renderResult();
            }
        }, 1500);
    }

    // 피드백 표시 함수
    function showFeedback(correct) {
        const feedback = document.createElement('div');
        feedback.className = `feedback ${correct ? 'correct' : 'wrong'}`;
        feedback.innerHTML = `
            <div class="feedback-text ${correct ? 'text-green-600' : 'text-red-600'}">
            ${correct ? '정답입니다! 🎉' : '틀렸습니다! 😢'}
            <div class="explanation mt-2 text-sm">
                ${questions[currentQuestion].explanation}
            </div>
        </div>
        `;
        document.getElementById('quiz-card').appendChild(feedback);

        setTimeout(() => feedback.remove(), 2000);
    }

    // 힌트 토글 함수 개선
    function toggleHint() {
        if (hintsUsed < 3) {
            showHint = !showHint;
            if (showHint) {
                hintsUsed++;
                const hintBubble = document.querySelector('.hint-bubble');
                if (hintBubble) {
                    hintBubble.classList.add('show');
                    // 3초 후 힌트 숨기기
                    if (hintBubbleTimeout) clearTimeout(hintBubbleTimeout);
                    hintBubbleTimeout = setTimeout(() => {
                        hintBubble.classList.remove('show');
                        showHint = false;
                    }, 3000);
                }
            }
            renderQuiz();
        }
    }

    // 전역 함수로 등록 (이벤트 핸들러에서 사용하기 위함)
    window.handleAnswer = handleAnswer;
    window.toggleHint = toggleHint;
    window.startQuiz = startQuiz;  // 추가

    renderStartScreen();
});