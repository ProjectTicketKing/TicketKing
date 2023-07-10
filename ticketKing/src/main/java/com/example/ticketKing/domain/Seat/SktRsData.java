package com.example.ticketKing.domain.Seat;

import com.example.ticketKing.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SktRsData<T> {

    private String status;
    private T data;

}
