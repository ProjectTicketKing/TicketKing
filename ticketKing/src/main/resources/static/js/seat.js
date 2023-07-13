let test = [];
let selectedSeats = new Array();
let selectedSeatsMap = [];
let seatWrapper;
let clicked = "";
let div = "";
let seatNum = "";

let seatRow = 0;
let seatColumn = 0;

let valid = [];
let secondValid = new Array();
let num = 0;


// const validSeats = [[${validSeats}]];
console.log('valid seats-->');

// console.log(validSeats);
// num = validSeats.length;

let stompClient = null;


<!--    console.log(validSeats.length);-->
<!--    console.log(validSeats[0].length);-->

console.log(valid[0]);
console.log(valid[1]);
console.log(valid[2]);

reverseMapping(valid[0])
reverseMapping(valid[1])
reverseMapping(valid[2])

function pdo(body) {
    const validSeats = body.validSeatsList;
    const columns = body.columns;
    const rows = body.rows;
    seatWrapper.replaceChildren();
    console.log(validSeats)
    for(let i=0; i < validSeats.length; i++){

        secondMapping(validSeats[i][0],validSeats[i][1]);
        valid.push(seatNum);
        secondValid.push(seatNum);
    }

    for (let i = 0; i < rows; i++) {
        div = document.createElement("div");
        seatWrapper.append(div);
        for (let j = 0; j < columns; j++) {
            const input = document.createElement('input');
            input.type = "button";
            input.name = "seats";
            input.classList.add("seat", "bg-gray-300", "text-transparent", "font-bold", "rounded-md", "p-2");
            // input.classList = "seat";
            mapping(input, i, j);
            div.append(input);


            <!--            백에서 처리 가능하면 백으로-->
            if (valid.includes(input.value)) {
                // input.classList.add("highlighted");
                input.classList.add("bg-purple-500", "text-transparent");
                input.addEventListener('click', function (e) {
                    console.log(e.target.value);
                    const [row, column] = reverseMapping(e.target.value);
                    // Toggle clicked class
                    input.classList.toggle("clicked");

                    clicked = document.querySelectorAll(".clicked");
                    selectedSeats = Array.from(clicked).map(data => data.value);
                    SeatClickEvent(row, column);
                    console.log(selectedSeats);
                });
            } else {
                input.disabled = true;
            }
        }
    }
}


function mapping(input, i, j) {
    const alphabet = String.fromCharCode(65 + i); // Convert numeric value to corresponding ASCII character
    input.value = alphabet + j.toString();
}

function secondMapping(i,j) {
    const alphabet = String.fromCharCode(65 + i);
    seatNum = alphabet + j.toString();

}



function reverseMapping(inputValue) {
    if (inputValue) { // inputValue가 존재하는 경우에만 함수 실행
        const alphabetIndex = inputValue.charCodeAt(0) - 65; // Convert ASCII character to numeric value
        const row = alphabetIndex;
        const column = parseInt(inputValue.slice(1));
        console.log(row);
        console.log(column);

        return [row, column];
    }

}



//소켓적용
function getSeatStatus() {
    console.log("getSeatStatus")


    fetch(`/api/usr/concert/${hall}/seats/${type}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }})
        .then(response => response.json())
        .then(body => {
            pdo(body);

            // drawSeat(body);
        });
}

// seat의 status를 출력해주는
function drawSeat(seat) {

    const status = `${seat.status} `;
    console.log(status);

}

// 클릭 이벤트 핸들러 등록
document.querySelectorAll('.seat').forEach(function(seat) {
    seat.addEventListener('click', function() {
        // 클릭한 좌석의 row와 column 값을 가져

        const row = seat.dataset.row;
        const column = seat.dataset.column;

        // SeatClickEvent 함수 호출 시 row와 column 값을 전달
        SeatClickEvent(row, column);
    });
});

// 클릭으로 row와 column (프론트에서 백으로 정보전달)
function SeatClickEvent(row, column) {
    const seatData = {

        row: row,
        column: column,
        hallName: hall,
        seatType: type

    };

    stompClient.send(`/app/seats/${hall}/${type}/seatInfo`, {}, JSON.stringify(seatData));
}


// 클릭으로 row와 column (프론트에서 백으로 정보전달)
function ConfirmClickEvent(row, column) {
    const seatData = {

        row: row,
        column: column,
        hallName: hall,
        seatType: type

    };

    stompClient.send(`/app/seats/${hall}/${type}/confirmInfo`, {}, JSON.stringify(seatData));

}



function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    const headers = {
        'X-CSRF-TOKEN': token,
    };

    stompClient.connect(headers, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe(`/topic/seats/${hall}/${type}`, function (seatData) {
            const parsedData = JSON.parse(seatData.body);


            if (parsedData.status === "invalid") {
                getSeatStatus();
                alert("실패");

                setTimeout(function () {
                    location.reload();
                }, 300);
            } else {
                alert("성공");
                console.log("성공 seatData.body 확인");
                console.log(parsedData.status);
                console.log(parsedData.data[0]);

                seatRow = parsedData.data[0];
                seatColumn = parsedData.data[1];

                const seatRowValueElement = document.getElementById('seatRowValue');
                seatRowValueElement.textContent = seatRow.toString();
                const seatColumnValueElement = document.getElementById('seatColumnValue');
                seatColumnValueElement.textContent = seatColumn.toString();


            }
        });
    });
}


document.addEventListener("DOMContentLoaded", function() {
    // ChatMessageUl = document.querySelector('.chat__message-ul');
    seatWrapper = document.querySelector(".seat-wrapper");
    connect();
    getSeatStatus();
});




function confirmSeat(){
    ConfirmClickEvent(seatRow, seatColumn);
    location.href = '/usr/concert/' + hall + '/' + selectedLevel + '/cost';
}
