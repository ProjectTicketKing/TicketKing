var timerInterval;
var isfinished = false;

function startGame() {
    var timerDisplay = document.getElementById('timer');
    var startButton = document.getElementById('startButton');
    var timeSelect = document.getElementById('timeSelect');
    var selectedTime = parseInt(timeSelect.value, 10);

    if (isNaN(selectedTime)) {
        timerDisplay.innerText = '00 : 00 : 00'; // 시간을 선택하지 않았을 경우 시간 표시를 00:00:00으로 설정
        return; // 함수 종료
    }

    var seconds = selectedTime;
    timerDisplay.innerText = formatTime(seconds);

    startButton.setAttribute('disabled', true); // 버튼 비활성화

    clearInterval(timerInterval); // 이전 타이머 중지
    timerInterval = setInterval(function() {
        if(isfinished) {
            seconds++;
        }
        else{
            seconds--;
        }
        timerDisplay.innerText = formatTime(seconds);

        if (seconds <= 0) {
            isfinished = true;
            startButton.innerText = '예매하기';
            startButton.removeAttribute('disabled'); // 버튼 활성화
            startButton.onclick = function() {
                window.location.href = '/usr/concert/date'; // 예매 페이지로 이동하는 로직 추가
            };
        }
    }, 1000);
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
