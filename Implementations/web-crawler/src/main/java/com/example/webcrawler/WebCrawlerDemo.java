package com.example.webcrawler;

import com.example.webcrawler.service.CrawlerManager;
import com.example.webcrawler.util.MockPageParser;
import com.example.webcrawler.util.MockStorageService;
import com.example.webcrawler.util.MockUrlFetcher;
import com.example.webcrawler.util.PageParser;
import com.example.webcrawler.util.StorageService;
import com.example.webcrawler.util.UrlFetcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawlerDemo {

    public static void main(String[] args) {
        // Step 1: Initialize ExecutorService for multithreading
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Step 2: Initialize the core services
        UrlFetcher urlFetcher = new MockUrlFetcher();
        PageParser pageParser = new MockPageParser();
        StorageService storageService = new MockStorageService();

        // Step 4: Define seed URLs and max depth for the simulation
        int maxDepth = 2;
        String[] seedUrls = {
                "http://example.com",
                "http://example.org",
                "http://example.net"
        };

        // Step 3: Initialize CrawlerManager with mock services
        CrawlerManager crawlerManager = new CrawlerManager(executorService, storageService, urlFetcher, pageParser, maxDepth);

        // Step 5: Start the crawl simulation with multiple seed URLs
        System.out.println("Starting web crawl simulation with seed URLs...");
        crawlerManager.startCrawl(seedUrls);
    }
}