package com.example.webcrawler.util;

public class MockUrlFetcher implements UrlFetcher {

    @Override
    public byte[] fetch(String url) {
        System.out.println("Fetching URL: " + url);
        return ("<html><body>Links in " + url + "</body></html>").getBytes();  // Simulated HTML content
    }
}