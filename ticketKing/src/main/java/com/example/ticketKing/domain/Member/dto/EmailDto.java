package com.example.ticketKing.domain.Member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailDto {
    private String to;
    private String subject;
    private String message;
}

