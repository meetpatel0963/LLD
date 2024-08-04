package org.example;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SlidingWindowLog {
    private final int maxRequests;
    private final long windowSize;
    private final Queue<Long> requestTimestamps;

    public SlidingWindowLog(int maxRequests, long windowSize, TimeUnit windowUnit) {
        this.maxRequests = maxRequests;
        this.windowSize = windowUnit.toMillis(windowSize);
        this.requestTimestamps = new LinkedList<>();
    }

    public synchronized boolean tryRequest() {
        long currentTime = System.currentTimeMillis();

        while (!requestTimestamps.isEmpty() && (currentTime - requestTimestamps.peek() >= windowSize)) {
            requestTimestamps.poll();
        }

        if (requestTimestamps.size() < maxRequests) {
            requestTimestamps.add(currentTime);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SlidingWindowLog slidingWindowLog = new SlidingWindowLog(10, 1, TimeUnit.SECONDS);

        Runnable task = () -> {
            if (slidingWindowLog.tryRequest()) {
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
