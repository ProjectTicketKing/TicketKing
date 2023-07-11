package com.example.ticketKing.domain.Competition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 요청을 처리하는 DTO 클래스
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ScheduleRequestDTO {
    private String level;
    private String hall;
    private String type;

    // 생성자, getter, setter 등 필요한 메서드 작성
}