package com.slippery.newsservice.service.impl;

import com.slippery.newsservice.models.NewsItem;
import com.slippery.newsservice.repository.NewsRepository;
import com.slippery.newsservice.service.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository repository;
    private final RestClient restClient;
    @Value("${FINN_API_URL}")
    private String FINN_HUB_API_URL;

    public NewsServiceImpl(NewsRepository repository, RestClient.Builder restClientBuilder) {
        this.repository = repository;
        this.restClient = restClientBuilder.build();
    }
    @Override
    public List<NewsItem> fetchNews(){
        var news = Arrays.stream(Objects.requireNonNull(restClient.get()
                .uri(FINN_HUB_API_URL)
                .retrieve()
                .body(NewsItem[].class))).toList();

        return news;
    }

}
