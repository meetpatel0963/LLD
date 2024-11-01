package com.example.webcrawler.util;

// Responsible for storing the crawled page content
public interface StorageService {
    void store(String url, byte[] content);
}