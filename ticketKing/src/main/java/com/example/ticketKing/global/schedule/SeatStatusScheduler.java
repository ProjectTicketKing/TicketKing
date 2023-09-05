package com.example.ticketKing.global.schedule;
import com.example.ticketKing.domain.Seat.service.SeatService;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SeatStatusScheduler {
    private ScheduledExecutorService executor;

    // env1 (실제 환경)에서의 스케쥴링
    public void startSeatStatusUpdateSchedule(SeatService seatService, String hall, String type, String level) {
        executor = Executors.newSingleThreadScheduledExecutor();

        long initialDelay = 0;
        long period = 0;
        if (level.equals("basic")) {
            initialDelay = 5000;
            period = 5000; // 5 seconds
        } else if (level.equals("advanced")) {
            initialDelay = 1000;
            period = 1000; // 3 seconds
        }
        executor.scheduleAtFixedRate(() -> seatService.updateRandomSeatStatusToInvalid(hall, type), initialDelay, period, TimeUnit.MILLISECONDS);
    }

    // env1 (실제 환경)에서의 스케쥴링 중단
    public void stopSeatStatusUpdateSchedule() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    // env2 (가상 환경)에서의 스케쥴링
    public void startVirtualSeatStatusUpdateSchedule(SeatService seatService, String hall, String type, String level) {
        executor = Executors.newSingleThreadScheduledExecutor();

        long initialDelay = 0;
        long period = 0;
        if (level.equals("basic")) {
            initialDelay = 5000;
            period = 5000; // 5 seconds
        } else if (level.equals("advanced")) {
            initialDelay = 1000;
            period = 1000; // 3 seconds
        }
        executor.scheduleAtFixedRate(() -> seatService.updateRandomVirtualSeatStatusToInvalid(hall, type), initialDelay, period, TimeUnit.MILLISECONDS);
    }
}
