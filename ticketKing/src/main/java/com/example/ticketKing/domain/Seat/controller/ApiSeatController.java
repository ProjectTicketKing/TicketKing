package com.example.ticketKing.domain.Seat.controller;

import com.example.ticketKing.domain.Seat.dto.SeatDto;
import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.domain.VirtualSeat.service.VirtualSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiSeatController {
    private final SeatService seatService;
    private final VirtualSeatService virtualSeatService;

    @GetMapping("/usr/{env}/concert/{hall}/seats/{type}")
    public ResponseEntity<SeatDto> getSeatList(@PathVariable("hall") String hall
            , @PathVariable("type") String type,@PathVariable("env")String env) {
        // 서비스 단에서 seatType이 뭔 지 확인해서 해당하는 row와 column 받아오는 코드 작성

        if(env.equals("realGame")){
            int rows = seatService.getRow(hall, type);
            int columns = seatService.getColumn(hall, type);

            // 유효한 좌석들
            int[][] validSeats;
            validSeats = seatService.getValidSeats(hall, type);

            // 2차원 배열을 리스트로 변환
            List<List<Integer>> validSeatsList = Arrays.stream(validSeats)
                    .map(row -> Arrays.stream(row).boxed().collect(Collectors.toList()))
                    .toList();

            SeatDto seatDto = SeatDto.builder()
                    .rows(rows)
                    .columns(columns)
                    .validSeatsList(validSeatsList)
                    .build();

            return ResponseEntity.ok(seatDto);
        }
        else if (env.equals("virtualGame"))
        {
            int rows = virtualSeatService.getRow(hall, type);
            int columns = virtualSeatService.getColumn(hall, type);

            // 유효한 좌석들
            int[][] validSeats;
            validSeats = virtualSeatService.getValidSeats(hall, type);

            // 2차원 배열을 리스트로 변환
            List<List<Integer>> validSeatsList = Arrays.stream(validSeats)
                    .map(row -> Arrays.stream(row).boxed().collect(Collectors.toList()))
                    .toList();

            SeatDto seatDto = SeatDto.builder()
                    .rows(rows)
                    .columns(columns)
                    .validSeatsList(validSeatsList)
                    .build();

            return ResponseEntity.ok(seatDto);
        }

        return null;

    }



}
