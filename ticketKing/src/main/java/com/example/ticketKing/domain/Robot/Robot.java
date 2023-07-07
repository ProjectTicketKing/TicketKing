package com.example.ticketKing.domain.Robot;

import java.util.UUID;

public class Robot {

    private String id;

    public Robot() {
        // 로봇의 ID 생성 로직
        this.id = generateRobotId();
    }

    public boolean tryReserve() {
        // 예약 시도 로직
        // 예약이 성공하면 true를 반환하고, 실패하면 false를 반환
        // 랜덤하게 예약 성공 또는 실패 여부를 결정하여 반환
        return Math.random() < 0.5; // 50%의 확률로 예약 성공
    }

    public String getId() {
        return id;
    }

    private String generateRobotId() {
        // 로봇의 ID 생성 로직을 구현
        // 랜덤한 문자열 생성
        return UUID.randomUUID().toString();
    }

}
