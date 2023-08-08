package com.example.ticketKing.domain.Seat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SktRsData<T> {

    private String status;
    private T data;

}
