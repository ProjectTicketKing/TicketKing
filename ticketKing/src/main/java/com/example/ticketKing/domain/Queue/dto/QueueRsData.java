package com.example.ticketKing.domain.Queue.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueueRsData<T> {

    private String status;
    private T data;

}
