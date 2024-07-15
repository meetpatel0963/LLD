package org.example;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LeakyBucket {
    private final int capacity;
    private final int leakRate;
    private final TimeUnit timeUnit;
    private final Queue<Date> queue;
    private final ScheduledExecutorService scheduler;

    public LeakyBucket(int capacity, int leakRate, TimeUnit timeUnit) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.timeUnit = timeUnit;
        queue = new LinkedList<>();
        scheduler = Executors.newScheduledThreadPool(1);
        startLeaking();
    }

    private void startLeaking() {
        scheduler.scheduleAtFixedRate(this::leak, 0, leakRate, timeUnit);
    }

    private synchronized void leak() {
        if (!queue.isEmpty()) {
            queue.poll();
            System.out.println("Leaked one request. Queue size: " + queue.size());
        }
    }

    public synchronized boolean addRequest(Date request) {
        if (queue.size() < capacity) {
            queue.add(request);
            System.out.println("Added request. Queue size: " + queue.size());
            return true;
        }
        System.out.println("Bucket full. Request denied.");
        return false;
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        LeakyBucket leakyBucket = new LeakyBucket(10, 1, TimeUnit.SECONDS);

        Runnable task = () -> {
            Date request = new Date();
            if (leakyBucket.addRequest(request)) {
                System.out.println("Request added: " + request);
            } else {
                System.out.println("Request denied: " + request);
            }
        };

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorService.shutdown();
            leakyBucket.shutdown();
        }));
    }
}
