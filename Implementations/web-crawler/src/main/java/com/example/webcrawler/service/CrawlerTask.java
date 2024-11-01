package com.example.webcrawler.service;

import java.util.List;

// Task representing a single crawl unit of work
class CrawlerTask implements Runnable {
    private final String url;
    private final int depth;
    private final int maxDepth;
    private final CrawlerManager manager;

    public CrawlerTask(String url, int depth, int maxDepth, CrawlerManager manager) {
        this.url = url;
        this.depth = depth;
        this.maxDepth = maxDepth;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            byte[] content = manager.getUrlFetcher().fetch(url);
            manager.getStorageService().store(url, content);

            if (depth < maxDepth) {
                List<String> urls = manager.getPageParser().parse(content);
                for (String childUrl : urls) {
                    manager.submitTask(childUrl, depth + 1);
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing URL: " + url + ", Error: " + e.getMessage());
        } finally {
            // Notify that this task is completed
            manager.taskCompleted();
        }
    }
}