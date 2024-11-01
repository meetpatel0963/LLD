package com.example.webcrawler.util;

import java.util.List;

// Responsible for parsing page content and extracting URLs
public interface PageParser {
    List<String> parse(byte[] content);
}