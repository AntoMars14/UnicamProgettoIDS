package it.unicam.model.controllersGRASP;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeController {
    private final ScheduledExecutorService executor;

    public TimeController() {
        this.executor = Executors.newScheduledThreadPool(1);
    }

    public void scheduleMidnightTask(Runnable task) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime midnight = LocalDateTime.of(now.toLocalDate(), LocalTime.MIDNIGHT);


        if (now.compareTo(midnight) > 0) {
            midnight = midnight.plusDays(1);
        }
        long initialDelay = now.until(midnight, ChronoUnit.SECONDS);
        executor.scheduleAtFixedRate(task, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

}
