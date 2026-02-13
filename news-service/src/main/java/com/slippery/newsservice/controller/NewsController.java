package com.slippery.newsservice.controller;

import com.slippery.newsservice.models.NewsItem;
import com.slippery.newsservice.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService service;

    public NewsController(NewsService service) {
        this.service = service;
    }
    @GetMapping
    public List<NewsItem> fetchNews(){
        return service.fetchNews();
    }
}
