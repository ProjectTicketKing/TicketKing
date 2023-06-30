let stompClient = null;
let fromId = 0;
let ChatMessageUl = null;


// 참가자 목록 가져오기 ( 사용 x, 추후 사용)
function getUserList() {
    fetch(`/usr/chat/rooms/${chatRoomId}/members`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    })
        .then(response => response.json())
        .then(members => {
            const userList = document.getElementById("userList");
            userList.innerHTML = ""; // 이전 목록 초기화

            members.forEach(member => {
                const listItem = document.createElement("a");
                listItem.classList.add("dropdown-item");
                listItem.textContent = member.username;
                userList.appendChild(listItem);
            });
        });
}

function scrollToBottom() {
    const chatMessages = document.querySelector('.chat-messages');
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

