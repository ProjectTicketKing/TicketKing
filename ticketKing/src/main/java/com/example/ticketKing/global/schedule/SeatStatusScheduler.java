package com.example.ticketKing.global.schedule;
import com.example.ticketKing.domain.Seat.service.SeatService;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SeatStatusScheduler {
    private ScheduledExecutorService realExecutor;
    private ScheduledExecutorService virtualExecutor;

    // env1 (실제 환경)에서의 스케쥴링
    public void startSeatStatusUpdateSchedule(SeatService seatService, String hall, String type) {
        realExecutor = Executors.newSingleThreadScheduledExecutor();

        long initialDelay = 0;
        long period = 0;

        initialDelay = 5000;
        period = 5000; // 5 seconds

        realExecutor.scheduleAtFixedRate(() -> seatService.updateRandomSeatStatusToInvalid(hall, type), initialDelay, period, TimeUnit.MILLISECONDS);
    }

    // env1 (실제 환경)에서의 스케쥴링 중단
    public void stopSeatStatusUpdateSchedule() {
        if (realExecutor != null && !realExecutor.isShutdown()) {
            realExecutor.shutdown();
        }
    }

    // env2 (가상 환경)에서의 스케쥴링
    public void startVirtualSeatStatusUpdateSchedule(SeatService seatService, String hall, String type, String level) {
        virtualExecutor = Executors.newSingleThreadScheduledExecutor();

        long initialDelay = 0;
        long period = 0;
        if (level.equals("basic")) {
            initialDelay = 5000;
            period = 5000; // 5 seconds
        } else if (level.equals("advanced")) {
            initialDelay = 1000;
            period = 1000; // 3 seconds
        }
        virtualExecutor.scheduleAtFixedRate(() -> seatService.updateRandomVirtualSeatStatusToInvalid(hall, type), initialDelay, period, TimeUnit.MILLISECONDS);
    }

    // env (가상 환경)에서의 스케쥴링 중단
    public void stopVirtualSeatStatusUpdateSchedule() {
        if (virtualExecutor != null && !virtualExecutor.isShutdown()) {
            virtualExecutor.shutdown();
        }
    }

}
