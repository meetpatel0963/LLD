package org.example;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenBucket {
    private final int maxTokens;
    private final int refillRate;
    private final TimeUnit timeUnit;
    private int tokens;
    private final ScheduledExecutorService scheduler;

    public TokenBucket(int maxTokens, int refillRate, TimeUnit timeUnit) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.timeUnit = timeUnit;
        this.tokens = maxTokens;
        scheduler = Executors.newScheduledThreadPool(1);
        startRefilling();
    }

    private void startRefilling() {
        scheduler.scheduleAtFixedRate(this::refill, 0, refillRate, timeUnit);
    }

    private synchronized void refill() {
        if (tokens < maxTokens) {
            tokens++;
        }
    }

    public synchronized boolean tryConsume() {
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        TokenBucket tokenBucket = new TokenBucket(10, 1, TimeUnit.SECONDS);
        Runnable task = () -> {
            if (tokenBucket.tryConsume()) {
                System.out.printf("Time: %s -> Token consumed, task executed.%n", new Date());
            } else {
                System.out.printf("Time: %s -> No tokens available, task denied.%n", new Date());
            }
        };

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorService.shutdown();
            tokenBucket.shutdown();
        }));
    }
}