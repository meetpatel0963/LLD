package com.example.webcrawler.util;

import java.util.ArrayList;
import java.util.List;

public class MockPageParser implements PageParser {

    @Override
    public List<String> parse(byte[] pageContent) {
        // Simulate finding two related URLs for each page fetched
        List<String> urls = new ArrayList<>();
        urls.add("http://example.com/page1");
        urls.add("http://example.com/page2");
        return urls;
    }
}