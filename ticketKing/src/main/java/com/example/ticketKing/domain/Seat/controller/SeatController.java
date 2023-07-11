package com.example.ticketKing.domain.Seat.controller;


import com.example.ticketKing.domain.Practice.service.PracticeService;
import com.example.ticketKing.domain.Seat.dto.SktRsData;
import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.global.rq.Rq;
import com.example.ticketKing.global.security.SecurityMember;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Controller
@Slf4j
public class SeatController {

    private final SeatService seatService;
    private final PracticeService practiceService;
    private final Rq rq;

    private ScheduledExecutorService executor;
    private Set<String> scheduledHalls = new HashSet<>();

    Integer finalRow = 0;
    Integer finalCol = 0;



    // 스케줄링 시작 메서드
    private void startSeatStatusUpdateSchedule(String hall, String type) {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> seatService.updateRandomSeatStatusToInvalid(hall, type), 5000, 5000, TimeUnit.MILLISECONDS);
    }

    // 스케줄링 중지 메서드
    private void stopSeatStatusUpdateSchedule() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    @GetMapping("/usr/concert/{hall}/seats/{type}")
    public String getSeatList(Model model, @PathVariable("hall") String hall, @PathVariable("type") String type) {
        model.addAttribute("hall", hall);
        model.addAttribute("type", type);

        // 스케줄링이 시작되지 않았을 때만 스케줄링 시작
        if (!scheduledHalls.contains(hall)) {
            startSeatStatusUpdateSchedule(hall, type);
            scheduledHalls.add(hall);
        }


        System.out.println("Confirm CHECk!!!!!!!!!!!!!");
        System.out.println("Confirm CHECk!!!!!!!!!!!!!");
        System.out.println("Confirm CHECk!!!!!!!!!!!!!");
        System.out.println(finalRow);
        System.out.println(finalCol);


        return "usr/concert/remain_seat";
    }

    // 애플리케이션 컨텍스트 종료 시 스케줄링 종료 메서드 호출
    @PreDestroy
    private void stopScheduledExecutorService() {
        stopSeatStatusUpdateSchedule();
    }

    @MessageMapping("/seats/{hall}/{type}/seatInfo")
    @SendTo("/topic/seats/{hall}/{type}")
    public SktRsData<List<Integer>> sendChatMessage(@DestinationVariable String hall, @DestinationVariable String type, SeatRequest request
                                                    ) {
        // 가져온 Seat의 status가 valid이면 => valid
        // 가져온 Seat의 status가 invalid이면 => invalid
        String status = seatService.checkSeatStatus(hall, type, request.getRow(), request.getColumn());
        Integer row = request.getRow();
        Integer column = request.getColumn();


        List<Integer> rowCol = new ArrayList<>();
        rowCol.add(row);
        rowCol.add(column);


        SktRsData seatData = new SktRsData(status,rowCol);

        // RsData 만들어서 status, message, data 만들어서
        // Signal Type
        // subscribe단에서 type을 줘서 프론트에서 if문을 써서 처리할 수 있도록
        // data generic타입으로
        // 프론트 단에서 리스트처리

        return seatData;

    }



    @MessageMapping("/seats/{hall}/{type}/confirmInfo")
//    @SendTo("/topic/seats/{hall}/{type}")
    public void sendConfirmMessage(@DestinationVariable String hall, @DestinationVariable String type, SeatRequest request,
                                   @AuthenticationPrincipal SecurityMember securityMember) {


        log.info("hall : {}", hall);
        log.info("type : {}", type);
        log.info("row : {}", request.getRow());
        log.info("column : {}", request.getColumn());

        log.info("securityMember : {}", securityMember);
        log.info("securityMember id : {} ", securityMember.getId());


        practiceService.register(hall,type, request.getRow(), request.getColumn(), securityMember.getId());



    }

}