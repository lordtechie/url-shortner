package com.example.url_shortner.controller;

import com.example.url_shortner.service.UrlShortnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlShortnerController {

    private final UrlShortnerService service;


    public UrlShortnerController(UrlShortnerService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public Map<String, String> shortenUrl(@RequestBody Map<String, String> request) {

        String originalUrl = request.get("url");
        String shortUrl = service.shortenUrl(originalUrl);

        return Map.of("shortUrl", shortUrl);
    }

    @GetMapping("/metrics/top-domains")
    public Map<String, Integer> topDomains() {
        return service.getTopDomains();
    }
}
