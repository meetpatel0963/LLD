package com.example.webcrawler.service;

import com.example.webcrawler.util.PageParser;
import com.example.webcrawler.util.StorageService;
import com.example.webcrawler.util.UrlFetcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

class CrawlerManagerTest {
    private CrawlerManager crawlerManager;
    private ExecutorService mockExecutorService;
    private UrlFetcher mockUrlFetcher;
    private PageParser mockPageParser;
    private StorageService mockStorageService;

    @BeforeEach
    void setUp() {
        // Mock all dependencies
        mockExecutorService = mock(ExecutorService.class);
        mockUrlFetcher = mock(UrlFetcher.class);
        mockPageParser = mock(PageParser.class);
        mockStorageService = mock(StorageService.class);

        // Mock behavior for ExecutorService to directly execute the Runnable
        doAnswer(invocation -> {
            Runnable task = invocation.getArgument(0);
//            task.run();
            return null;
        }).when(mockExecutorService).execute(any(Runnable.class));

        // Initialize CrawlerManager with mocked dependencies
        crawlerManager = new CrawlerManager(
                mockExecutorService,
                mockStorageService,
                mockUrlFetcher,
                mockPageParser,
                2 // Set max depth to 2 for testing
        );
    }

    @AfterEach
    void tearDown() {
        crawlerManager.shutdown();
    }

    @Test
    void testStartCrawl_withSeedUrls() throws Exception {
        String[] seedUrls = {"http://example.com"};
        // Start the crawl
        new Thread(() -> crawlerManager.startCrawl(seedUrls)).start();
        Thread.sleep(2000);
        assertEquals(1, crawlerManager.getTaskCount().get());
        crawlerManager.taskCompleted();
        assertEquals(0, crawlerManager.getTaskCount().get());
    }

    @Test
    void testSubmitTask_whenUrlIsVisited() {
        String url = "http://example.com";
        crawlerManager.submitTask(url, 0);

        // Attempt to submit the same URL again
        crawlerManager.submitTask(url, 1);

        // Ensure only one task is actually submitted for the same URL
        assertEquals(1, crawlerManager.getTaskCount().get());
    }

    @Test
    void testSubmitTask_whenDepthExceedsMaxDepth() {
        String url = "http://example.com";
        crawlerManager.submitTask(url, 3); // Depth 3 exceeds maxDepth of 2

        // Ensure that task is not submitted due to max depth constraint
        assertEquals(0, crawlerManager.getTaskCount().get());
    }

    @Test
    void testTaskCompleted_decrementsTaskCount() {
        // Simulate task submission
        crawlerManager.submitTask("http://example.com", 0);
        assertEquals(1, crawlerManager.getTaskCount().get());

        // Complete the task
        crawlerManager.taskCompleted();

        // Verify that the task count is decremented
        assertEquals(0, crawlerManager.getTaskCount().get());
    }
}
