var timerInterval;
var isfinished = false;
var queueCount = 0; // 대기열 고객 수
var startTime; // Variable to store the start time
var timeDifference;
var levelSelect;
var selectedLevel;
var typeValue;
//var hallValue = "[[${hallValue}]]";// Access the hallValue from Thymeleaf
//var hallValue = "KSPO";
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

    // AJAX 요청을 통해 선택한 레벨 값을 백엔드로 전송
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/start-schedule', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // 요청이 성공적으로 처리되었을 때의 동작
            console.log('레벨 값 전송 완료');

            var responseData = JSON.parse(xhr.responseText);
            var hallValue = responseData.hall;
            typeValue = responseData.type;

            // 여기서 예매 페이지로 이동하도록 처리
            window.location.href = '/usr/concert/' + hallValue + '/seats/' + typeValue;
        }
    };
    xhr.send(JSON.stringify({
        hall: hallValue,
        type: typeValue,
        level: selectedLevel
    }));


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

var timeInterval2 = null;

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
            timerInterval2 = setTimeout(function() {
                closeModal();
            }, 7000); // 7초 후에 자동으로 닫힘 (대기 시간)
        } else if (timeDifference >= 300 && timeDifference < 500) {
            timerInterval2 = setTimeout(function() {
                closeModal();
            }, 10000); // 10초 후에 자동으로 닫힘 (대기 시간)
        } else {
            timerInterval2 = setTimeout(function() {
                closeModal();
            }, 15000); // 15초 후에 자동으로 닫힘 (대기 시간)
        }
    } else {
        // 고급 난이도 선택 시 다른 대기 시간 설정
        if (timeDifference >= 0 && timeDifference < 200) {
            timerInterval2 = setTimeout(function() {
                closeModal();
            }, 10000); // 10초 후에 자동으로 닫힘 (대기 시간)
        } else if (timeDifference >= 200 && timeDifference < 400) {
            timerInterval2 = setTimeout(function() {
                closeModal();
            }, 15000); // 15초 후에 자동으로 닫힘 (대기 시간)
        } else {
            timerInterval2 = setTimeout(function() {
                closeModal();

            }, 20000); // 20초 후에 자동으로 닫힘 (대기 시간)
        }
    }

}

// 대기열 모달 닫기 및 보안 문자 모달 열기
function closeModal() {
    document.getElementById('modal').style.display = 'none';
    openCaptchaModal();
}


function checkCaptcha() {
    var captchaInput = document.getElementById('captchaInput').value;
    var expectedAnswers = ["263S2V", "263s2v"]; // 정답

    if (expectedAnswers.includes(captchaInput)) {
        // 검증 성공시 모달 닫기
        closeCaptchaModal();
    } else {
        // 보안 문자가 올바르지 않다면 알림 처리
        alert("보안 문자가 올바르지 않습니다.");
    }
}


// 보안 문자 모달 열기
function openCaptchaModal() {
    document.getElementById('modal2').style.display = 'block';
}

// 보안 문자 모달 닫기
function closeCaptchaModal() {
    document.getElementById('modal2').style.display = 'none';

    // 다른 페이지로 이동 (여기서는 '예매 페이지'로 이동하도록 설정)
    location.href = '/usr/concert/' + hallValue + '/date'; // 예매 페이지로 이동하는 로직 추가

}


// timeDifference에 따라 다른 메시지를 보여주는 함수
function showMessage() {
    var queueCountElement = document.getElementById('queueCount');

    if (selectedLevel === "basic") {
        // 초급 난이도 선택 시 다른 대기 인원 설정
        if (timeDifference >= 0 && timeDifference < 300) {
            decreaseQueueCount(150, 1, 7000); //7초
        } else if (timeDifference >= 300 && timeDifference < 500) {
            decreaseQueueCount(330, 1, 10000); //10초
        } else if (timeDifference >= 500) {
            decreaseQueueCount(1000, 1, 15000); //15초
        }
    } else if (selectedLevel === "advanced") {
        // 고급 난이도 선택 시 다른 대기 인원 설정
        if (timeDifference >= 0 && timeDifference < 200) {
            decreaseQueueCount(300, 1, 10000); //10초
        } else if (timeDifference >= 200 && timeDifference < 400) {
            decreaseQueueCount(550, 1, 15000); //15초
        } else if (timeDifference >= 400) {
            decreaseQueueCount(2000, 1, 20000); //20초
        }
    }

    function decreaseQueueCount(startCount, endCount, duration) {
        var decrementAmount = 9; // 9명씩 감소
        var steps = Math.ceil(Math.abs(startCount - endCount) / decrementAmount); // 단계 수 계산
        var stepDuration = duration / steps;
        var currentCount = startCount;

        var decreaseInterval = setInterval(function() {
            if (currentCount > endCount) {
                queueCountElement.innerText = currentCount;
                currentCount -= decrementAmount;
                if (currentCount < endCount) {
                    currentCount = endCount; // 최소값으로 설정
                }
            } else {
                queueCountElement.innerText = endCount;
                clearInterval(decreaseInterval);
            }
        }, stepDuration);
    }

}