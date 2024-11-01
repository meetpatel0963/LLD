package com.example.webcrawler.util;

// Responsible for fetching page content
public interface UrlFetcher {
    byte[] fetch(String url);
}