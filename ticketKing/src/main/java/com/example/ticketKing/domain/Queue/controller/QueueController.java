package com.example.ticketKing.domain.Queue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QueueController {

    // 예매하기 요청을 처리하는 컨트롤러 메서드
    @MessageMapping("/app/reserve")
    public void reserveTicket(String userName) {
        // userName을 이용한 예매 처리 로직을 작성
        // 예매에 성공하면 대기열에 추가하거나 다른 작업을 수행할 수 있습니다.
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private List<String> queue = new ArrayList<>(); // 대기열 리스트

    @MessageMapping("/addUserToQueue")
    public void addUserToQueue(String username) {
        if (!queue.contains(username)) {
            queue.add(username);
        }
        sendQueueCount();
    }

    @MessageMapping("/removeUserFromQueue")
    public void removeUserFromQueue(String username) {
        queue.remove(username);
        sendQueueCount();
    }

    private void sendQueueCount() {
        messagingTemplate.convertAndSend("/topic/queueCount", queue.size());
    }
}