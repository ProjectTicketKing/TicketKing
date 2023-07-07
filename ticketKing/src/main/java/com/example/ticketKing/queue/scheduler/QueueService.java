package com.example.ticketKing.queue.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
@Service
@RequiredArgsConstructor
public class QueueService {
    private final RedisTemplate<String,Object> redisTemplate;
    // Atomicity: 연산이 끝까지 실행되는 동안 다른 스레드에서 해당 값을 변경할 수 없음
    // 대기열 진입 순서를 기록하기 위한 Counter
    private final AtomicLong queueCounter = new AtomicLong(0);

    //TODO: 로봇 객체 선언
    //Redis에 저장된 Sorted Set의 크기를 체크하고, 크기가 0인지 (즉, 대기열이 비어 있는지) 확인
    //대기열이 비어있을 때까지 반복
    //만약 대기열에 아무도 없다면, "모든 유저가 예매를 완료하였습니다."라는 로그 메시지를 출력하고 스케줄러를 종료되도록
    public boolean isQueueEmpty() {
        return redisTemplate.opsForZSet().size("queue")==0;
    }

    // 예매하기 버튼을 누를 때 대기열에 진입하는 로봇과 사용자의 순서를 정함
    // enterQueue : 예매하기 버튼을 누를 때 사용자와 로봇을 대기열에 진입시키는 역할
    public void enterQueue(String userId, String robotId) {
        long queueNumber = queueCounter.incrementAndGet(); // 대기열 진입 순서 증가

        // 로봇과 사용자를 대기열에 추가
        redisTemplate.opsForZSet().add("queue", userId, queueNumber);
        redisTemplate.opsForZSet().add("queue:robot", robotId, queueNumber);
    }

    // 대기열에서 사용자를 가져옴
    // processTicketReservation : 예매 로직을 수행하고, 사용자와 로봇의 대기열 처리를 진행,
    // 예매 로직을 수행하고 성공 또는 실패에 따라 사용자와 로봇이 대기열에서 제거되거나 다시 대기열에 추가되도록
    @Transactional
    public void processTicketReservation() {
        // 대기열에서 사용자 가져오기
        Set<Object> userIds = redisTemplate.opsForZSet().range("queue", 0, 0);
        Set<Object> robotIds = redisTemplate.opsForZSet().range("queue:robot", 0, 0);

        if (userIds.isEmpty() || robotIds.isEmpty()) {
            log.error("대기열에서 사용자 또는 로봇을 가져오지 못했습니다.");
            return;
        }

        String userId = (String) userIds.iterator().next();
        String robotId = (String) robotIds.iterator().next();

        // 예매 로직 수행
        boolean reservationSuccess = ticketReservation(userId);

        if (reservationSuccess) {
            // 예매 성공: 대기열에서 사용자 제거
            redisTemplate.opsForZSet().remove("queue", userId);
            redisTemplate.opsForZSet().remove("queue:robot", robotId);
        } else {
            // 예매 실패: 사용자를 다시 대기열에 넣기
            double failedTimestamp = System.currentTimeMillis() / 1000.0;
            redisTemplate.opsForZSet().add("queue", userId, failedTimestamp);
            redisTemplate.opsForZSet().add("queue:robot", robotId, failedTimestamp);
        }
    }


    // 실제로 티켓을 예매하고, 예매 성공 여부를 반환하는 역할을 수행해야 함
    // 사용 가능한 좌석이 있는지 확인한 후, 좌석 상태가 "valid"(예매 가능)이면 예매를 수행
    // 사용 가능한 좌석이 없거나 좌석 상태가 "invalid"이면 false를 반환하여 예매에 실패했음을 알림
    private boolean ticketReservation(String userId) {
        // 1. 사용 가능한 좌석이 있는지 확인
        String seatStatus = seatService.checkSeatStatus("hallName", "type", row, column);

        if ("valid".equals(seatStatus)) {
            // TODO: 예매 로직 수행 (실제로 티켓을 예매하는 코드를 작성)

            seatService.updateRandomSeatStatusToInvalid("hallName", "type");

            // 예매 성공
            return true;
        } else {
            // 좌석이 없거나 "invalid" 상태이므로 예매 실패
            return false;
        }
    }
}
