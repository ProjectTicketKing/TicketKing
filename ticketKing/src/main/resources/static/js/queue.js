var timerInterval;
var isfinished = false;
var startTime; // Variable to store the start time

let stompClient = null;
// Event listener for DOMContentLoaded to record the start time when the button is loaded
document.addEventListener("DOMContentLoaded", function() {
    var startButton = document.getElementById('startButton');
    startButton.addEventListener('click', startGame); // startGame 함수를 click 이벤트 핸들러로 할당
});

function startGame() {
    var timerDisplay = document.getElementById('timer');
    var startButton = document.getElementById('startButton');
    var startDateInput = document.getElementById('startDateInput'); // 추가: 날짜 입력 요소

    // 추가: 날짜를 선택하지 않았을 때 경고 메시지 표시
    if (!startDateInput.value || startDateInput.value === '관람일') {
        toastWarning('날짜를 선택해주세요.');
        return;
    }

    if (isfinished) {
        timerDisplay.innerText = '00 : 00 : 00';
        console.log('!!!');
        // toastWarning('게임 시작을 위해 모든 항목을 선택해주세요.');
        reserve();
        openQueueModal(); // 대기열 모달창 열기
        // openCaptchaModal();
        return;
    }

    // 아직 타이머가 동작 중이라면 아무 작업도 수행하지 않음
    if (timerInterval) {
        return;
    }

    // 타이머 시작 시간 기록
    startTime = new Date();

    // 타이머 시작
    timerInterval = setInterval(function() {
        var currentTime = new Date();
        var elapsedTime = Math.floor((currentTime - startTime) / 1000); // 경과 시간 (초)

        var remainingTime = 10 - elapsedTime; // 10초 후에 예매하기 버튼으로 변경

        if (remainingTime <= 0) {
            // 타이머가 종료되면 예매하기 버튼으로 변경
            isfinished = true;
            clearInterval(timerInterval);
            startButton.innerText = '예매하기';
            startButton.removeAttribute('disabled');

            // WebSocket 연결 설정
            connect();

        } else {
            timerDisplay.innerText = formatTime(remainingTime);
        }
    }, 1000);

    // 버튼 비활성화
    startButton.setAttribute('disabled', true);
}

function formatTime(seconds) {
    var hours = Math.floor(seconds / 3600);
    var minutes = Math.floor((seconds % 3600) / 60);
    var remainingSeconds = seconds % 60;

    var formattedTime = `${padZero(hours)} : ${padZero(minutes)} : ${padZero(remainingSeconds)}`;
    return formattedTime;
}

function padZero(value) {
    return value < 10 ? '0' + value : value;
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    const headers = {
        'X-CSRF-TOKEN': token,
    };

    stompClient.connect(headers, function (frame, userName) {
        // 연결 성공 시, 사용자를 대기열에 추가
        stompClient.send("/app/addUserToQueue", {}, userName);


        stompClient.subscribe("/topic/queueCount", function (message) {
            var queueCount = JSON.parse(message.body);
            // 대기 인원 업데이트
            document.getElementById('queueCount').innerText = queueCount;
        });

        // WebSocket 연결이 설정된 후에 대기열 모달 열기
        openQueueModal();
    });
}

function reserve() {
    var userName = getUserName();

    if (!userName) {
        // 사용자 ID가 없을 경우 처리 (예: 로그인되지 않은 경우)
        return;
    }

    stompClient.send("/app/reserve", {}, userName); // userName을 서버로 전송
}

// 사용자 ID를 얻는 로직을 구현해야 합니다.
function getUserName() {
    // Spring Security를 통해 현재 사용자의 아이디(username)를 얻는 방법
    var userName = null;
    $.ajax({
        url: '/api/getUserName', // 사용자 정보를 반환하는 API 엔드포인트
        type: 'GET',
        async: false,
        success: function (data) {
            userName = data.username;
            console.log('사용자 이름:', userName); // 사용자 이름을 콘솔에 출력
        },
        error: function () {
            // 오류 처리
            console.error('오류 발생'); // 오류 발생 시 콘솔에 오류 메시지 출력

        }
    });

    return userName;
}



// 대기열 모달 열기
function openQueueModal() {
    // WebSocket을 통해 서버에 연결
    stompClient.connect({}, function (frame) {
        // 연결 성공 시, 사용자를 대기열에 추가
        stompClient.send("/app/addUserToQueue", {}, "RealParticipant");

        document.getElementById('modal').style.display = 'block';
    });
}

// 대기열 모달 닫기 및 보안 문자 모달 열기
function closeModal() {
    // WebSocket 연결을 해제
    stompClient.send("/app/removeUserFromQueue", {}, "RealParticipant");
    stompClient.disconnect();

    document.getElementById('modal').style.display = 'none';
    openCaptchaModal();
}



function checkCaptcha() {
    document.getElementById("captchaInput").value = "";
    captcha = document.getElementById("image");
    let uniquechar = "";

    const randomchar =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (let i = 1; i < 6; i++) {
        uniquechar += randomchar.charAt(
            Math.random() * randomchar.length)
    }

    captcha.innerHTML = uniquechar;
}

function captchaprintmsg(){
    const captchaInput = document
        .getElementById("captchaInput").value;
    if (captchaInput.toUpperCase() === captcha.innerHTML.toUpperCase()) {
        let s = document.getElementById("key")
            .innerHTML = "Matched";
        // 검증 성공시 모달 닫기
        closeCaptchaModal();
    } else {
        let s = document.getElementById("key")
            .innerHTML = "not Matched";
        // 보안 문자가 올바르지 않다면 알림 처리
        toastWarning("보안 문자가 올바르지 않습니다.");
    }
}

// 보안 문자 모달 열기
function openCaptchaModal() {
    document.getElementById('modal2').style.display = 'block';
}

// 보안 문자 모달 닫기
function closeCaptchaModal() {
    document.getElementById('modal2').style.display = 'none';

    location.href = '/usr/' + environment + '/' + userId + '/concert/' + hallValue +'/date'; // 예매 페이지로 이동하는 로직 추가

}