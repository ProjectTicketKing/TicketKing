package com.example.ticketKing.queue.sevice;

import com.example.ticketKing.queue.scheduler.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueScheduler {
    private final QueueService queueService;

    // 1초마다 도는 대기열 동기화 스케줄러 구성
    // 메소드가 종료된 시점으로부터 1초 후에 다시 실행되도록 설정

    @Scheduled(fixedDelay = 1000)
    private void queueService(){
        // 큐에 남아있는 유저가 없으면 메소드 종료
        if(queueService.isQueueEmpty()){
            log.info("===== 모든 유저가 예매를 완료하였습니다. =====");
            return;
        }
        queueService.processTicketReservation();  // 예매 로직 처리 메소드 호출
    }
}
