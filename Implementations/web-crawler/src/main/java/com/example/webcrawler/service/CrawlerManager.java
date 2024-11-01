package com.example.webcrawler.service;

import com.example.webcrawler.util.PageParser;
import com.example.webcrawler.util.StorageService;
import com.example.webcrawler.util.UrlFetcher;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class CrawlerManager {
    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
    private final ExecutorService executorService;
    private final UrlFetcher urlFetcher;
    private final PageParser pageParser;
    private final StorageService storageService;
    private final int maxDepth;
    private final AtomicInteger taskCount; // To track the number of submitted tasks
    private final CountDownLatch latch; // Latch to wait for all tasks to complete

    public CrawlerManager(ExecutorService executorService,
                          StorageService storageService,
                          UrlFetcher urlFetcher,
                          PageParser pageParser,
                          int maxDepth) {
        this.executorService = executorService;
        this.storageService = storageService;
        this.urlFetcher = urlFetcher;
        this.pageParser = pageParser;
        this.maxDepth = maxDepth;
        this.taskCount = new AtomicInteger(0);
        this.latch = new CountDownLatch(1);
    }

    // Start crawling with multiple seed URLs
    public void startCrawl(String[] seedUrls) {
        for (String url : seedUrls) {
            submitTask(url, 0); // Submit each seed URL as a task
        }
        try {
            latch.await(); // Wait for all tasks to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            shutdown(); // Shut down after all tasks are complete
        }
    }

    public void submitTask(String url, int depth) {
        if (depth <= maxDepth && visitedUrls.add(url)) {
            taskCount.incrementAndGet(); // Increment task count
            executorService.submit(new CrawlerTask(url, depth, maxDepth, this));
        }
    }

    public void taskCompleted() {
        if (taskCount.decrementAndGet() == 0) {
            latch.countDown(); // Decrease the latch count when all tasks are completed
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
    }

}
