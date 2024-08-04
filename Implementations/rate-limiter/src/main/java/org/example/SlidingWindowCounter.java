package org.example;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SlidingWindowCounter {
    private final int maxRequests;
    private final long windowSize;
    private int prevWindowRequestCount;
    private int curWindowRequestCount;
    private long windowStart;

    public SlidingWindowCounter(int maxRequests, int windowSize, TimeUnit windowUnit) {
        this.maxRequests = maxRequests;
        this.windowSize = windowUnit.toMillis(windowSize);
        this.curWindowRequestCount = 0;
        this.prevWindowRequestCount = 0;
        this.windowStart = System.currentTimeMillis();
    }

    public synchronized boolean tryRequest() {
        long curTime = System.currentTimeMillis();
        long elapsedTime = curTime - windowStart;

        // Check if we need to slide the window
        if (elapsedTime >= windowSize) {
            windowStart = curTime;
            prevWindowRequestCount = curWindowRequestCount;
            curWindowRequestCount = 0;
            elapsedTime = 0; // Reset elapsed time after sliding the window
        }

        // Calculate the overlap ratio
        double overlapRatio = (double) elapsedTime / windowSize;

        // Calculate the weighted sum of requests
        double weightedRequestCount = prevWindowRequestCount * (1 - overlapRatio) + curWindowRequestCount;

        // Check if the current request can be allowed
        if (weightedRequestCount < maxRequests) {
            curWindowRequestCount++;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SlidingWindowCounter slidingWindowCounter = new SlidingWindowCounter(10, 1, TimeUnit.SECONDS);

        Runnable task = () -> {
            if (slidingWindowCounter.tryRequest()) {
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
