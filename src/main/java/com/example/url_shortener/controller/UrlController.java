package com.example.url_shortener.controller;

import com.example.url_shortener.dto.UrlRequest;
import com.example.url_shortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;


    @PostMapping("/shorten")
    public String shorten(@RequestBody @Valid UrlRequest request){
        return urlService.shorten(request.originalUrl());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {

        String originalUrl = urlService.getOriginalUrl(code);

        if (originalUrl == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
