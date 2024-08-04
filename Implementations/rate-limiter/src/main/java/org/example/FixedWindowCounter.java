package org.example;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FixedWindowCounter {
    private final int maxRequests;
    private final long windowSize;
    private long windowStart;
    private int requestCount;

    public FixedWindowCounter(int maxRequests, int windowSize, TimeUnit windowUnit) {
        this.maxRequests = maxRequests;
        this.windowSize = windowUnit.toMillis(windowSize);
        this.windowStart = System.currentTimeMillis();
        this.requestCount = 0;
    }

    public synchronized boolean tryRequest() {
        long curTime = System.currentTimeMillis();
        if (curTime - windowStart >= windowSize) {
            windowStart = curTime;
            requestCount = 0;
        }

        if (requestCount < maxRequests) {
            requestCount++;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        FixedWindowCounter fixedWindowCounter = new FixedWindowCounter(10, 1, TimeUnit.SECONDS);

        Runnable task = () -> {
            if (fixedWindowCounter.tryRequest()) {
                System.out.println(new Date() + " -> Request allowed.");
            } else {
                System.out.println(new Date() + " -> Request denied.");
            }
        };

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(task, 0, 50, TimeUnit.MILLISECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));
    }
}
