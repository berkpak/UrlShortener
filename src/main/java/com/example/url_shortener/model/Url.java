package com.example.url_shortener.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "urls")
@Data
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortCode;

    @Column(unique = false)
    private String originalUrl;
    private Long clickCount = 0L;

}
