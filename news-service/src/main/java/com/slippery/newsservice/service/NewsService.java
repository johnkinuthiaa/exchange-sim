package com.slippery.newsservice.service;

import com.slippery.newsservice.models.NewsItem;

import java.util.List;

public interface NewsService {
    List<NewsItem> fetchNews();

}
