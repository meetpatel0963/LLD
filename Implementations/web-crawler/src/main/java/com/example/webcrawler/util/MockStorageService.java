package com.example.webcrawler.util;

public class MockStorageService implements StorageService {

    @Override
    public void store(String url, byte[] content) {
        System.out.println("Storing content of URL: " + url);
    }
}