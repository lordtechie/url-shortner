package com.example.url_shortner.service;

import com.example.url_shortner.util.Base62Encoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Service
public class UrlShortnerService {

    private final Map<String,String> longToShort = new HashMap<>();
    private final Map<String,String> shortToLong = new HashMap<>();
    private final Map<String,Integer> domainCount = new HashMap<>();

    private final AtomicLong counter = new AtomicLong(1);
    private static final String BASE_URL = "http://localhost:8082/u/";

    public String shortenUrl(String originalUrl) {

        if (longToShort.containsKey((originalUrl))) {
            return longToShort.get(originalUrl);
        }

        String shortCode = Base62Encoder.encode(counter.getAndIncrement());
        String shortUrl = BASE_URL + shortCode;

        longToShort.put(originalUrl, shortUrl);
        shortToLong.put(shortCode, originalUrl);

        return shortUrl;
    }

    public String getOriginalUrl(String shortCode) {
        return shortToLong.get(shortCode);
    }

    public Map<String, Integer> getTopDomains() {
        return domainCount.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost().replace("www.", "");
        } catch (Exception e) {
            return "unknown";
        }
    }

}
