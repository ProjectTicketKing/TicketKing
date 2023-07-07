var timerInterval;
var isfinished = false;
var queueCount = 0; // 대기열 고객 수
var startTime; // Variable to store the start time
var timeDifference;
var levelSelect;
var selectedLevel;

// Event listener for DOMContentLoaded to record the start time when the button is loaded
document.addEventListener("DOMContentLoaded", function() {
  var startButton = document.getElementById('startButton');
  startButton.addEventListener('click', startGame); // startGame 함수를 click 이벤트 핸들러로 할당
});


function startGame() {
    var timerDisplay = document.getElementById('timer');
    var startButton = document.getElementById('startButton');
    var timeSelect = document.getElementById('timeSelect');
    var selectedTime = parseInt(timeSelect.value, 10);

//    var levelSelect = document.getElementById('levelSelect');

    levelSelect = document.getElementById('levelSelect');
    selectedLevel = levelSelect.value; // 사용자가 선택한 레벨 값



    if (isNaN(selectedTime)) {
        timerDisplay.innerText = '00 : 00 : 00';
        return;
    }

    var seconds = selectedTime;
    timerDisplay.innerText = formatTime(seconds);

    startButton.setAttribute('disabled', true);

    clearInterval(timerInterval);

    timerInterval = setInterval(function() {
        if (isfinished) {
            seconds++;
        } else {
            seconds--;
        }
        timerDisplay.innerText = formatTime(seconds);

        if (seconds <= 0) {
            isfinished = true;
            startButton.innerText = '예매하기';
            startTime = new Date();
            startButton.removeAttribute('disabled');
            startButton.onclick = function() {
                var endTime = new Date();
                timeDifference = endTime - startTime;
                console.log("Time taken to click Start button:", timeDifference + " ms");

                openModal();

                showMessage(); // 메시지를 보여주는 함수 호출


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

function openModal() {
    var modal = document.getElementById('modal');
    var queueCountElement = document.getElementById('queueCount');

    // 대기열 수 증가
    queueCount++;
    queueCountElement.innerText = queueCount;

    // 대기열 모달 창 열기
    modal.style.display = 'flex';

    // 타이머가 종료되면 모달을 자동으로 닫도록 설정
    if (selectedLevel === "basic") {
         // 초급 난이도 선택 시 다른 대기 시간 설정
         if (timeDifference >= 0 && timeDifference < 300) {
             timerInterval = setInterval(function() {
                 closeModal();
             }, 10000); // 10초 후에 자동으로 닫힘 (대기 시간)
         } else if (timeDifference >= 300 && timeDifference < 500) {
             timerInterval = setInterval(function() {
                 closeModal();
             }, 20000); // 20초 후에 자동으로 닫힘 (대기 시간)
         } else if (timeDifference >= 500) {
             timerInterval = setInterval(function() {
                 closeModal();
            }, 30000); // 30초 후에 자동으로 닫힘 (대기 시간)
         }
    } else if (selectedLevel === "advanced") {
        // 고급 난이도 선택 시 다른 대기 시간 설정
        if (timeDifference >= 0 && timeDifference < 200) {
             timerInterval = setInterval(function() {
                 closeModal();
             }, 20000); // 20초 후에 자동으로 닫힘 (대기 시간)
        } else if (timeDifference >= 200 && timeDifference < 400) {
             timerInterval = setInterval(function() {
                  closeModal();
             }, 30000); // 30초 후에 자동으로 닫힘 (대기 시간)
        } else if (timeDifference >= 400) {
             timerInterval = setInterval(function() {
                  closeModal();
            }, 40000); // 40초 후에 자동으로 닫힘 (대기 시간)
        }
    }


    // 모달 닫기 버튼 클릭 이벤트
    var modalCloseButton = document.getElementById('modalCloseButton');
    modalCloseButton.onclick = function() {
      clearInterval(timerInterval); // 타이머 중지
      closeModal(); // 모달 창 닫기
    };
}

function closeModal() {
    var modal = document.getElementById('modal');
    var queueCountElement = document.getElementById('queueCount');

    // 대기열 수 초기화
    queueCount = 0;
    queueCountElement.innerText = queueCount;

    // 대기열 모달 창 닫기
    modal.style.display = 'none';

    // 다른 페이지로 이동 (여기서는 '예매 페이지'로 이동하도록 설정)
      window.location.href = '/usr/concert/date'; // 예매 페이지로 이동하는 로직 추가
}

// timeDifference에 따라 다른 메시지를 보여주는 함수
function showMessage() {
    var queueCountElement = document.getElementById('queueCount');

    if (selectedLevel === "basic") {
        // 초급 난이도 선택 시 다른 대기 인원 설정
        if (timeDifference >= 0 && timeDifference < 300) {
            queueCountElement.innerText = parseInt(queueCountElement.innerText) + 33;
        } else if (timeDifference >= 300 && timeDifference < 500) {
            queueCountElement.innerText = parseInt(queueCountElement.innerText) + 120;
        } else if (timeDifference >= 500) {
            queueCountElement.innerText = parseInt(queueCountElement.innerText) + 1300;
        }
    } else if (selectedLevel === "advanced") {
        // 고급 난이도 선택 시 다른 대기 인원 설정
        if (timeDifference >= 0 && timeDifference < 200) {
            queueCountElement.innerText = parseInt(queueCountElement.innerText) + 120;
        } else if (timeDifference >= 200 && timeDifference < 400) {
            queueCountElement.innerText = parseInt(queueCountElement.innerText) + 700;
        } else if (timeDifference >= 400) {
            queueCountElement.innerText = parseInt(queueCountElement.innerText) + 3000;
        }
    }

}