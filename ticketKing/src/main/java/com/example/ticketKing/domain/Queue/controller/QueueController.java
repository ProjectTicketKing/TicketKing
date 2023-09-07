package com.example.ticketKing.domain.Queue.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QueueController {

    private List<String> queue = new ArrayList<>(); // 대기열 리스트

    // 예매하기 눌렀을 때
    // 사용자가 대기열에 입장하는 시점
    // 사용자 이름을 받아서 대기열에 추가하고 대기열의 인원 수를 업데이트
    @MessageMapping("/addUserToQueue")
    @SendTo("/topic/initialQueueCount")
    public void addUserToQueue(String username) {
        if (!queue.contains(username)) {
            queue.add(username);
        }
        getQueueCount(queue);
    }


    //TODO - 특정 시간 이후로 3초마다 5명씩 queue배열에서 제거해주고
    @Scheduled(fixedDelay = 3000)
    public void removeUsersFromQueue() {
        int removalCount = Math.min(5, queue.size()); // 최대 5명 삭제
        for (int i = 0; i < removalCount; i++) {
            String removedUser = queue.remove(0); // 대기열에서 첫 번째 사용자 제거
        }
    }

    @Scheduled(fixedDelay = 3000)
    @SendTo("/topic/queueSize")
    public int sendQueueSizeToClient() {
        return queue.size();
    }


    public int getQueueCount(List<String> queue){
        return queue.size();
    }

    // 보안문자 창에 들어왔을 때
    // 사용자가 대기열에서 나간 시점
    // 사용자 이름을 받아서 대기열에서 해당 사용자를 제거하고 인원 수를 업데이트


}