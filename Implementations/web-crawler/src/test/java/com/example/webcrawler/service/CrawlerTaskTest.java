package com.example.webcrawler.service;

import com.example.webcrawler.util.PageParser;
import com.example.webcrawler.util.StorageService;
import com.example.webcrawler.util.UrlFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CrawlerTaskTest {
    private CrawlerManager mockCrawlerManager;
    private UrlFetcher mockUrlFetcher;
    private StorageService mockStorageService;
    private PageParser mockPageParser;
    private final String testUrl = "http://example.com";
    private final int depth = 0;
    private final int maxDepth = 2;

    @BeforeEach
    void setUp() {
        // Create mocks for dependencies
        mockUrlFetcher = mock(UrlFetcher.class);
        mockStorageService = mock(StorageService.class);
        mockPageParser = mock(PageParser.class);
        mockCrawlerManager = mock(CrawlerManager.class);

        // Set up the manager's behaviors
        when(mockCrawlerManager.getUrlFetcher()).thenReturn(mockUrlFetcher);
        when(mockCrawlerManager.getStorageService()).thenReturn(mockStorageService);
        when(mockCrawlerManager.getPageParser()).thenReturn(mockPageParser);
    }

    @Test
    void testRun_fetchesContent_and_storesIt() {
        // Define behavior for mocks
        byte[] mockContent = "<html>Mock Content</html>".getBytes();
        when(mockUrlFetcher.fetch(testUrl)).thenReturn(mockContent);
        when(mockPageParser.parse(mockContent)).thenReturn(List.of("http://example.com/page1", "http://example.com/page2"));

        // Create the task
        CrawlerTask task = new CrawlerTask(testUrl, depth, maxDepth, mockCrawlerManager);

        // Run the task
        task.run();

        // Verify that the fetch method was called with the correct URL
        verify(mockUrlFetcher).fetch(testUrl);

        // Verify that the content was stored correctly
        verify(mockStorageService).store(testUrl, mockContent);

        // Verify that the page parser was called to parse the content
        verify(mockPageParser).parse(mockContent);

        // Verify that new tasks were submitted for the parsed URLs
        verify(mockCrawlerManager).submitTask("http://example.com/page1", depth + 1);
        verify(mockCrawlerManager).submitTask("http://example.com/page2", depth + 1);

        // Verify that taskCompleted was called at the end of the run
        verify(mockCrawlerManager).taskCompleted();
    }

    @Test
    void testRun_doesNotSubmitTasks_whenAtMaxDepth() {
        // Create a task at max depth
        CrawlerTask task = new CrawlerTask(testUrl, maxDepth, maxDepth, mockCrawlerManager);

        // Run the task
        task.run();

        // Verify that no new tasks are submitted since max depth is reached
        verify(mockCrawlerManager, never()).submitTask(anyString(), anyInt());

        // Verify that taskCompleted was called at the end of the run
        verify(mockCrawlerManager).taskCompleted();
    }

    @Test
    void testRun_handlesExceptions_gracefully() {
        // Make the fetch method throw an exception
        when(mockUrlFetcher.fetch(testUrl)).thenThrow(new RuntimeException("Fetch error"));

        // Create the task
        CrawlerTask task = new CrawlerTask(testUrl, depth, maxDepth, mockCrawlerManager);

        // Run the task (it should handle the exception without failing)
        task.run();

        // Verify that taskCompleted was called even after an exception
        verify(mockCrawlerManager).taskCompleted();
    }
}
