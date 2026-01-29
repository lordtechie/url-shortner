package com.example.url_shortner.controller;

import com.example.url_shortner.service.UrlShortnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {

    private final UrlShortnerService service;


    public RedirectController(UrlShortnerService service) {
        this.service = service;
    }

    @GetMapping("/u/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {

        String originalUrl = service.getOriginalUrl(code);

        if(originalUrl == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
