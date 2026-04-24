package com.example.url_shortener.service;

import com.example.url_shortener.model.Url;
import com.example.url_shortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public String shorten(String originalUrl){

        validateUrl(originalUrl);

        String code = generateCode();

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(code);

        urlRepository.save(url);

        return code;

    }

    public String getOriginalUrl(String code) {
        return urlRepository.findByShortCode(code)
                .map(Url::getOriginalUrl)
                .orElse(null);
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0,6);
    }

    private void validateUrl(String url) {
        try {
            new URI(url);
        } catch (Exception e) {
            throw new RuntimeException("Invalid URL");
        }
    }
}


